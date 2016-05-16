package ru.nbakaev.hishop.purchaseorder.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 *
 * Create purchase status for {@link ru.nbakaev.hishop.purchaseorder.PurchaseOrder}
 */
@Service
public class PurchaseStatusRepositoryImpl implements PurchaseStatusRepository {

    private final MongoOperations mongoTemplate;

    @Autowired
    public PurchaseStatusRepositoryImpl(final MongoOperations mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public PurchaseStatus createNewPurchaseStatus(PurchaseStatus status) {
        mongoTemplate.insert(status);
        return status;
    }

    @Override
    public List<PurchaseStatus> getAllPurchaseStatuses() {
        Criteria criteria = new Criteria();
        Query query = new Query(criteria);

        return mongoTemplate.find(query, PurchaseStatus.class);
    }

    @Override
    public PurchaseStatus updatePurchaseStatus(PurchaseStatus status) {
        mongoTemplate.save(status);
        return status;
    }

    @Override
    public void deletePurchaseStatusById(String id) {
        PurchaseStatus status = new PurchaseStatus();
        status.setId(id);
        mongoTemplate.remove(status);

    }
}