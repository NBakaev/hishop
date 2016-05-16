package ru.nbakaev.hishop.purchaseorder.status;

import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/9/2016.
 * All Rights Reserved
 */
public interface PurchaseStatusRepository {
    PurchaseStatus createNewPurchaseStatus(PurchaseStatus status);

    List<PurchaseStatus> getAllPurchaseStatuses();

    PurchaseStatus updatePurchaseStatus(PurchaseStatus status);

    void deletePurchaseStatusById(String id);
}
