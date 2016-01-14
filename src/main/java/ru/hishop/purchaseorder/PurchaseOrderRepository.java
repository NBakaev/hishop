package ru.hishop.purchaseorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */
@Service
public class PurchaseOrderRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public PurchaseOrder createNewPurchaseOrder(PurchaseOrder good) {
        mongoTemplate.insert(good);
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

    public void deletePurchaseOrderById (String id){
        PurchaseOrder good = new PurchaseOrder();
        good.setId(id);
        mongoTemplate.remove(good);
    }

}