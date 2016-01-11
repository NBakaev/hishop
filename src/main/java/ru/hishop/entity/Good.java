package ru.hishop.entity;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */

@Document
public class Good extends BasicClassAbstract {

    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}