package ru.nbakaev.hishop.purchaseorder;

import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/9/2016.
 * All Rights Reserved
 */
public interface PurchaseOrderRepository {
    PurchaseOrder createNewPurchaseOrder(PurchaseOrder good);

    List<PurchaseOrder> getAllPurchaseOrder();
    List<PurchaseOrder> getPurchaseOrderForUser(String userId);

    PurchaseOrder updatePurchaseOrder(PurchaseOrder good);

    void deletePurchaseOrderById(String id);
}
