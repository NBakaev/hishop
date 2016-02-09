package tk.hishopapp.purchaseorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
public class PurchaseOrderRepository {

    private final MongoTemplate mongoTemplate;
    private final RestTemplate restTemplate;

    @Autowired
    public PurchaseOrderRepository(final MongoTemplate mongoTemplate, final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.mongoTemplate = mongoTemplate;
    }

    // this is default google analytics url
    // t = type
    // tid = id of account in GA
    private String baseUrl = "http://www.google-analytics.com/collect?v=1&tid=UA-73503004-1&t=transaction";

    private void sendPurchaseToGoogleAnalytics(PurchaseOrder purchaseOrder) {
        String clientId = purchaseOrder.getCreatedInfo().getCreatedById();
        if (clientId == null || clientId.length() == 0) {
            clientId = "default_user";
        }

        StringBuilder builder = new StringBuilder(baseUrl);
        builder.append("&ti=").append(purchaseOrder.getId());
        builder.append("&ta=").append("hishopapp.tk");
        builder.append("&tr=").append(purchaseOrder.getPrice());
        builder.append("&ta=").append(purchaseOrder.getPrice());
        builder.append("&cid=").append(clientId);

        Object response = new Object();
        restTemplate.getForEntity(builder.toString(), Object.class, response);
    }

    public PurchaseOrder createNewPurchaseOrder(PurchaseOrder good) {
        mongoTemplate.insert(good);
        try {
            sendPurchaseToGoogleAnalytics(good);
        } catch (RestClientException e) {
            // here we have an exception that we can not convert response from
            // google analytics response because that is image/gif - zero pixel
            // so, we do not interested in this image
            // and we have not default HttpMessageConverter
            // just ignore it
        }

        return good;
    }

    public List<PurchaseOrder> getAllPurchaseOrder() {

        Criteria criteria = new Criteria();
        Query query = new Query(criteria);

        return mongoTemplate.find(query, PurchaseOrder.class);
    }

    public PurchaseOrder updatePurchaseOrder(PurchaseOrder good) {
        mongoTemplate.save(good);
        return good;
    }

    public void deletePurchaseOrderById(String id) {
        PurchaseOrder good = new PurchaseOrder();
        good.setId(id);
        mongoTemplate.remove(good);
    }

}