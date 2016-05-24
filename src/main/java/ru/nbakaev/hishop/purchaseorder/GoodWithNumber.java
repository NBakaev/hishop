package ru.nbakaev.hishop.purchaseorder;

import ru.nbakaev.hishop.good.Good;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 5/24/2016.
 * All Rights Reserved
 */
public class GoodWithNumber {

    private Good good;
    private int number;

    public GoodWithNumber() {
    }

    public GoodWithNumber(Good good, int number) {
        this.good = good;
        this.number = number;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
