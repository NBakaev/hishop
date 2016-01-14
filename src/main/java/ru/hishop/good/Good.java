package ru.hishop.good;

import org.springframework.data.mongodb.core.mapping.Document;
import ru.hishop.entity.BasicClassAbstract;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */

@Document
public class Good extends BasicClassAbstract {

    private double price;

    /**
     * true to show on / page
     */

    private boolean showOnIndexPage = false;


    public boolean isShowOnIndexPage() {
        return showOnIndexPage;
    }

    public void setShowOnIndexPage(boolean showOnIndexPage) {
        this.showOnIndexPage = showOnIndexPage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}