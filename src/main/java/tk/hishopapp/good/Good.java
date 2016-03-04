package tk.hishopapp.good;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import tk.hishopapp.entity.BasicClassAbstract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */

@Document
@ApiModel("Good")
public class Good extends BasicClassAbstract {

    @ApiModelProperty("Price of good as double")
    private double price;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Good good = (Good) o;

        if (Double.compare(good.price, price) != 0) return false;
        if (numberAvailable != good.numberAvailable) return false;
        if (numberSold != good.numberSold) return false;
        if (showOnIndexPage != good.showOnIndexPage) return false;
        if (description != null ? !description.equals(good.description) : good.description != null) return false;
        return categoriesIds != null ? categoriesIds.equals(good.categoriesIds) : good.categoriesIds == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(price);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + numberAvailable;
        result = 31 * result + numberSold;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (categoriesIds != null ? categoriesIds.hashCode() : 0);
        result = 31 * result + (showOnIndexPage ? 1 : 0);
        return result;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}