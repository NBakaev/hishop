package ru.nbakaev.hishop.purchaseorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.nbakaev.hishop.configs.InvalidRequestException;
import ru.nbakaev.hishop.email.EmailSenderService;
import ru.nbakaev.hishop.entity.CreatedInfo;
import ru.nbakaev.hishop.good.Good;
import ru.nbakaev.hishop.good.GoodRepository;
import ru.nbakaev.hishop.purchaseorder.status.PurchaseStatusRepository;
import ru.nbakaev.hishop.settings.ShopSettingsRepository;
import ru.nbakaev.hishop.users.CurrentUser;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 * <p>
 * Google Analytics Url
 *
 * @see {https://developers.google.com/analytics/devguides/collection/protocol/v1/parameters?hl=ru}
 * @see {https://developers.google.com/analytics/devguides/collection/protocol/v1/devguide}
 */
@Service
public class PurchaseOrderRepositoryImpl implements PurchaseOrderRepository {

    private final MongoOperations mongoTemplate;
    private final RestTemplate restTemplate;
    private final EmailSenderService emailSenderService;
    private final CurrentUser currentUser;
    private final PurchaseStatusRepository purchaseStatusRepository;
    private final ShopSettingsRepository shopSettingsRepository;
    private final GoodRepository goodRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public PurchaseOrderRepositoryImpl(final MongoOperations mongoTemplate, final RestTemplate restTemplate, final EmailSenderService emailSenderService, final CurrentUser currentUser,
                                       final PurchaseStatusRepository purchaseStatusRepository, final ShopSettingsRepository shopSettingsRepository, final GoodRepository goodRepository,
                                       @Value("${google.analytics.tid}") String googleAnalyticsId,
                                       @Value("${google.analytics.ta}") String googleAnalyticsTA) {
        this.restTemplate = restTemplate;
        this.mongoTemplate = mongoTemplate;
        this.emailSenderService = emailSenderService;
        this.currentUser = currentUser;
        this.purchaseStatusRepository = purchaseStatusRepository;
        this.shopSettingsRepository = shopSettingsRepository;
        this.baseUrl = googleAnalyticsId;
        this.googleAnalyticsTA = googleAnalyticsTA;
        this.goodRepository = goodRepository;
    }

    // this is default google analytics url
    // t = type
    // tid = id of account in GA
    private final String baseUrl;
    private final String googleAnalyticsTA;

    private void sendPurchaseToGoogleAnalytics(PurchaseOrder purchaseOrder) {
        String clientId = purchaseOrder.getCreatedInfo().getCreatedById();
        if (clientId == null || clientId.length() == 0) {
            clientId = "default_user";
        }

        StringBuilder builder = new StringBuilder(baseUrl);
        builder.append("&ti=").append(purchaseOrder.getId());
        builder.append("&ta=").append(googleAnalyticsTA);
        builder.append("&tr=").append(purchaseOrder.getPrice());
        builder.append("&ta=").append(purchaseOrder.getPrice());
        builder.append("&cid=").append(clientId);

        Object response = new Object();
        restTemplate.getForEntity(builder.toString(), Object.class, response);
    }

    @Override
    public PurchaseOrder createNewPurchaseOrder(PurchaseOrder purchaseOrder) {

        // this is trick which can be replaced with AOP
        purchaseOrder.setCreatedInfo(new CreatedInfo(new Date(), currentUser.getCurrentUser().getId()));

        // do not allow user to override prices etc...
        List<GoodWithNumber> goodsFromDatabase = purchaseOrder.getGoodList().stream().map(x -> {
            x.setGood(goodRepository.getGoodById(x.getGood().getId()));
            return x;
        }).collect(Collectors.toList());
        purchaseOrder.setGoodList(goodsFromDatabase);


        // sum price of every good
        BigDecimal goodPriceSum = goodsFromDatabase.stream().map(x -> x.getGood().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        purchaseOrder.setPrice(goodPriceSum);

        // check that we have such number of goods
        goodsFromDatabase.stream().forEach(x -> {
            if (x.getGood().getNumberAvailable() < x.getNumber()) {
                throw new InvalidRequestException("No such number of goods available now. Please try later");
            }
        });

        // decrease available number of goods
        // and increase sold
        goodsFromDatabase.stream().forEach(x -> {
            Good good = x.getGood();
            good.setNumberAvailable(x.getGood().getNumberAvailable() - x.getNumber());
            good.setNumberSold(x.getGood().getNumberSold() + x.getNumber());
            goodRepository.updateGood(good);
        });

        purchaseOrder.setPaid(false);
        purchaseOrder.setRefunded(false);

        // this is first status
        purchaseOrder.setPurchaseStatus(shopSettingsRepository.getShopSettings().getFirstPurchaseStatus());

        mongoTemplate.insert(purchaseOrder);
        try {
            sendPurchaseToGoogleAnalytics(purchaseOrder);
        } catch (RestClientException e) {
            // here we have an exception that we can not convert response from
            // google analytics response because that is image/gif - zero pixel
            // so, we do not interested in this image
            // and we have not default HttpMessageConverter
            // just ignore it
        }

        try {
            emailSenderService.sendOrderEmail(purchaseOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("New order: {}", purchaseOrder.toString());

        return purchaseOrder;
    }

    @Override
    public List<PurchaseOrder> getAllPurchaseOrder() {

        Criteria criteria = new Criteria();
        Query query = new Query(criteria);

        return mongoTemplate.find(query, PurchaseOrder.class);
    }

    @Override
    public PurchaseOrder updatePurchaseOrder(PurchaseOrder good) {
        mongoTemplate.save(good);
        return good;
    }

    @Override
    public void deletePurchaseOrderById(String id) {
        PurchaseOrder good = new PurchaseOrder();
        good.setId(id);
        mongoTemplate.remove(good);
    }

}