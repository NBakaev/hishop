package ru.nbakaev.hishop.good;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.nbakaev.hishop.entity.BasicClassAbstract;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */

@Document
@ApiModel("Good")
public class Good extends BasicClassAbstract {

    @ApiModelProperty("Price of good as BigDecimal")
    private BigDecimal price;

    @ApiModelProperty("Price of good that we bought. Used to calculate marginality BigDecimal")
    private BigDecimal purchasePrice;

    @ApiModelProperty("Number that can be bought")
    private int numberAvailable;

    @ApiModelProperty("Number that was sold")
    private int numberSold;

    @ApiModelProperty("Some user-defined description in html")
    @TextIndexed
    private String description;

    @ApiModelProperty("Ids of categories")
    private List<String> categoriesIds = new ArrayList<>();

    @ApiModelProperty("true to show on / page")
    private boolean showOnIndexPage = false;

    @ApiModelProperty("Supplier id")
    private String companySupplierId;

    @ApiModelProperty("")
    private String mainAvatarUrl;

    private List<String> avatarUrl;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Good good = (Good) o;

        if (numberAvailable != good.numberAvailable) return false;
        if (numberSold != good.numberSold) return false;
        if (showOnIndexPage != good.showOnIndexPage) return false;
        if (price != null ? !price.equals(good.price) : good.price != null) return false;
        if (purchasePrice != null ? !purchasePrice.equals(good.purchasePrice) : good.purchasePrice != null)
            return false;
        if (description != null ? !description.equals(good.description) : good.description != null) return false;
        if (categoriesIds != null ? !categoriesIds.equals(good.categoriesIds) : good.categoriesIds != null)
            return false;
        if (companySupplierId != null ? !companySupplierId.equals(good.companySupplierId) : good.companySupplierId != null)
            return false;
        if (mainAvatarUrl != null ? !mainAvatarUrl.equals(good.mainAvatarUrl) : good.mainAvatarUrl != null)
            return false;
        return avatarUrl != null ? avatarUrl.equals(good.avatarUrl) : good.avatarUrl == null;

    }

    @Override
    public int hashCode() {
        int result = price != null ? price.hashCode() : 0;
        result = 31 * result + (purchasePrice != null ? purchasePrice.hashCode() : 0);
        result = 31 * result + numberAvailable;
        result = 31 * result + numberSold;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (categoriesIds != null ? categoriesIds.hashCode() : 0);
        result = 31 * result + (showOnIndexPage ? 1 : 0);
        result = 31 * result + (companySupplierId != null ? companySupplierId.hashCode() : 0);
        result = 31 * result + (mainAvatarUrl != null ? mainAvatarUrl.hashCode() : 0);
        result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
        return result;
    }

    public List<String> getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(List<String> avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getMainAvatarUrl() {
        return mainAvatarUrl;
    }

    public void setMainAvatarUrl(String mainAvatarUrl) {
        this.mainAvatarUrl = mainAvatarUrl;
    }

    public String getCompanySupplierId() {
        return companySupplierId;
    }

    public void setCompanySupplierId(String companySupplierId) {
        this.companySupplierId = companySupplierId;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getNumberAvailable() {
        return numberAvailable;
    }

    public void setNumberAvailable(int numberAvailable) {
        this.numberAvailable = numberAvailable;
    }

    public int getNumberSold() {
        return numberSold;
    }

    public void setNumberSold(int numberSold) {
        this.numberSold = numberSold;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCategoriesIds() {
        return categoriesIds;
    }

    public void setCategoriesIds(List<String> categoriesIds) {
        this.categoriesIds = categoriesIds;
    }

    public boolean isShowOnIndexPage() {
        return showOnIndexPage;
    }

    public void setShowOnIndexPage(boolean showOnIndexPage) {
        this.showOnIndexPage = showOnIndexPage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}