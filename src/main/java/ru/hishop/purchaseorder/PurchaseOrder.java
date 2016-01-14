package ru.hishop.purchaseorder;

import org.springframework.data.mongodb.core.mapping.Document;
import ru.hishop.entity.BasicClassAbstract;
import ru.hishop.good.Good;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */

@Document
public class PurchaseOrder extends BasicClassAbstract {

    private double price;

    /**
     * Does this order is full paid
     */
    private boolean paid = false;

    private List<Good> goodList = new ArrayList<>();


    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public List<Good> getGoodList() {
        return goodList;
    }

    public void setGoodList(List<Good> goodList) {
        this.goodList = goodList;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}