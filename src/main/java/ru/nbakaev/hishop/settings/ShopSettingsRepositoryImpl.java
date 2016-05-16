package ru.nbakaev.hishop.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import ru.nbakaev.hishop.purchaseorder.status.PurchaseStatus;
import ru.nbakaev.hishop.purchaseorder.status.PurchaseStatusRepository;

import javax.annotation.PostConstruct;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 5/16/2016.
 * All Rights Reserved
 */
@Service
public class ShopSettingsRepositoryImpl implements ShopSettingsRepository {

    private final MongoOperations mongoTemplate;
    private final PurchaseStatusRepository purchaseStatusRepository;

    @Autowired
    public ShopSettingsRepositoryImpl(final MongoOperations mongoTemplate, final PurchaseStatusRepository purchaseStatusRepository) {
        this.mongoTemplate = mongoTemplate;
        this.purchaseStatusRepository = purchaseStatusRepository;
    }

    @PostConstruct
    private void init (){
        ShopSettings shopSettings = this.getShopSettings();

        if (shopSettings == null){
            initShopSettings();
        }

    }

    /**
     * Set first status for {@link ru.nbakaev.hishop.purchaseorder.PurchaseOrder}
     * @param shopSettings
     */
    private void processShopSettingsPrimaryStatus(ShopSettings shopSettings){
        PurchaseStatus status = new PurchaseStatus("Заказ создан");
        purchaseStatusRepository.createNewPurchaseStatus(status);

        shopSettings.setFirstPurchaseStatus(status);
    }

    @Override
    public ShopSettings initShopSettings() {
        ShopSettings shopSettings = new ShopSettings();

        processShopSettingsPrimaryStatus(shopSettings);

        mongoTemplate.insert(shopSettings);
        return shopSettings;
    }

    @Override
    public ShopSettings getShopSettings() {
        Criteria criteria = new Criteria();
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query, ShopSettings.class);
    }

    @Override
    public ShopSettings updateShopSettings(ShopSettings shopSettings) {
        mongoTemplate.save(shopSettings);
        return shopSettings;
    }
}
