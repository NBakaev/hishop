package ru.hishop.purchaseorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 *
 * Google Analytics Url
 * https://developers.google.com/analytics/devguides/collection/protocol/v1/parameters?hl=ru
 */
@Service
public class PurchaseOrderRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RestTemplate restTemplate;

    private String baseUrl = "http://www.google-analytics.com/collect?v=1&tid=UA-61444-1&cid=1743432.14&t=transaction";

    public void sendToGoogleAnalytics(PurchaseOrder purchaseOrder) {
        StringBuilder builder = new StringBuilder(baseUrl);
        builder.append("&ti=" + purchaseOrder.getId());
        builder.append("&ta=" + "hishop.ru");
        builder.append("&tr=" + purchaseOrder.getPrice());
        builder.append("&ta=" + purchaseOrder.getPrice());
        builder.append("&iq=" + "1");

        String url = builder.toString();

        GoogleAnalyticsResponse response = new GoogleAnalyticsResponse();
        restTemplate.getForEntity(url, GoogleAnalyticsResponse.class, response);
    }

    public PurchaseOrder createNewPurchaseOrder(PurchaseOrder good) {
        mongoTemplate.insert(good);
        sendToGoogleAnalytics(good);
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