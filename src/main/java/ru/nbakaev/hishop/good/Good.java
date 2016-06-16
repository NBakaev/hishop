package ru.nbakaev.hishop.good;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.index.TextIndexed;
import ru.nbakaev.hishop.entity.BasicClassAbstract;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */
@ApiModel("Good")
public class Good extends BasicClassAbstract {

    @ApiModelProperty("Price of good as BigDecimal")
    private BigDecimal price = new BigDecimal(BigInteger.ZERO);

    @ApiModelProperty("Number that can be bought")
    private int numberAvailable;

    @ApiModelProperty("Number that was sold")
    private int numberSold;

    @ApiModelProperty("Some user-defined description in html")
    @TextIndexed
    private String description;

    @ApiModelProperty("Ids of categories")
    private List<String> categoriesIds = new ArrayList<>();

    @ApiModelProperty("URL of Main image of this good")
    private String mainAvatarUrl;

    @ApiModelProperty("Additional URLs of images for this good")
    private List<String> avatarUrl = new ArrayList<>();

    @ApiModelProperty("List of labels for good")
    private List<Label> labels = new ArrayList<>();


    public Good() {
    }

    public Good(String name) {
        super(name);
    }

    public List<Label> getLabels() {

        return labels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Good good = (Good) o;

        if (numberAvailable != good.numberAvailable) return false;
        if (numberSold != good.numberSold) return false;
        if (price != null ? !price.equals(good.price) : good.price != null) return false;
        if (description != null ? !description.equals(good.description) : good.description != null) return false;
        if (categoriesIds != null ? !categoriesIds.equals(good.categoriesIds) : good.categoriesIds != null)
            return false;
        if (mainAvatarUrl != null ? !mainAvatarUrl.equals(good.mainAvatarUrl) : good.mainAvatarUrl != null)
            return false;
        if (avatarUrl != null ? !avatarUrl.equals(good.avatarUrl) : good.avatarUrl != null) return false;
        return labels != null ? labels.equals(good.labels) : good.labels == null;

    }

    @Override
    public int hashCode() {
        int result = price != null ? price.hashCode() : 0;
        result = 31 * result + numberAvailable;
        result = 31 * result + numberSold;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (categoriesIds != null ? categoriesIds.hashCode() : 0);
        result = 31 * result + (mainAvatarUrl != null ? mainAvatarUrl.hashCode() : 0);
        result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
        result = 31 * result + (labels != null ? labels.hashCode() : 0);
        return result;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
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


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}