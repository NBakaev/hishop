package tk.hishopapp.purchaseorder;

import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/9/2016.
 * All Rights Reserved
 */
public interface PurchaseOrderRepository {
    PurchaseOrder createNewPurchaseOrder(PurchaseOrder good);

    List<PurchaseOrder> getAllPurchaseOrder();

    PurchaseOrder updatePurchaseOrder(PurchaseOrder good);

    void deletePurchaseOrderById(String id);
}
