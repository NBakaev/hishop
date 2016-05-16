package ru.nbakaev.hishop.settings;

import ru.nbakaev.hishop.entity.BasicClassAbstract;
import ru.nbakaev.hishop.purchaseorder.status.PurchaseStatus;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 5/16/2016.
 * All Rights Reserved
 */
public class ShopSettings extends BasicClassAbstract {

    private PurchaseStatus firstPurchaseStatus;


    public PurchaseStatus getFirstPurchaseStatus() {
        return firstPurchaseStatus;
    }

    public void setFirstPurchaseStatus(PurchaseStatus firstPurchaseStatus) {
        this.firstPurchaseStatus = firstPurchaseStatus;
    }
}
