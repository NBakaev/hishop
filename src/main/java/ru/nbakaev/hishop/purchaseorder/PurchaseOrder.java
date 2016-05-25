package ru.nbakaev.hishop.purchaseorder;

import io.swagger.annotations.ApiModelProperty;
import ru.nbakaev.hishop.entity.BasicClassAbstract;
import ru.nbakaev.hishop.purchaseorder.status.PurchaseStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * getCreatedInfo().getCreatedById() contain buyer
 *
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */
public class PurchaseOrder extends BasicClassAbstract {

    @ApiModelProperty("Price of good")
    private BigDecimal price;

    @ApiModelProperty("Does this order is full paid")
    private boolean paid = false;

    @ApiModelProperty("Is it refunded")
    private boolean refunded = false;

    @ApiModelProperty("Ids of goods in order")
    private List<GoodWithNumber> goodList = new ArrayList<>();

    @ApiModelProperty("Purchase status")
    private PurchaseStatus purchaseStatus = new PurchaseStatus();


    public PurchaseStatus getPurchaseStatus() {
        return purchaseStatus;
    }

    public void setPurchaseStatus(PurchaseStatus purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void setRefunded(boolean refunded) {
        this.refunded = refunded;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public List<GoodWithNumber> getGoodList() {
        return goodList;
    }

    public void setGoodList(List<GoodWithNumber> goodList) {
        this.goodList = goodList;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "PurchaseOrder{" +
                "price=" + price +
                ", paid=" + paid +
                ", refunded=" + refunded +
                ", goodList=" + goodList +
                ", purchaseStatus=" + purchaseStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchaseOrder that = (PurchaseOrder) o;

        if (paid != that.paid) return false;
        if (refunded != that.refunded) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (goodList != null ? !goodList.equals(that.goodList) : that.goodList != null) return false;
        return purchaseStatus != null ? purchaseStatus.equals(that.purchaseStatus) : that.purchaseStatus == null;

    }

    @Override
    public int hashCode() {
        int result = price != null ? price.hashCode() : 0;
        result = 31 * result + (paid ? 1 : 0);
        result = 31 * result + (refunded ? 1 : 0);
        result = 31 * result + (goodList != null ? goodList.hashCode() : 0);
        result = 31 * result + (purchaseStatus != null ? purchaseStatus.hashCode() : 0);
        return result;
    }
}