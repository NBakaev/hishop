package ru.nbakaev.hishop.purchaseorder.status;

import ru.nbakaev.hishop.entity.BasicClassAbstract;

/**
 * Status for for {@link ru.nbakaev.hishop.purchaseorder.PurchaseOrder}
 * for example {"created", "paid", "shipped"}
 * Created by Nikita Bakaev, ya@nbakaev.ru on 5/16/2016.
 * All Rights Reserved
 */
public class PurchaseStatus extends BasicClassAbstract {

    public PurchaseStatus(String name) {
        super(name);
    }

    public PurchaseStatus() {
    }
}
