package ru.nbakaev.hishop.entity.filters.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 4/15/2016.
 * All Rights Reserved
 */
@ApiModel
public class GoodFilterRequestDto extends BasicFilterRequestDto {

    @ApiModelProperty("Goods with price more than")
    private Double priceMoreThan = null;

    @ApiModelProperty("Goods with price less than")
    private Double priceLessThan = null;

    private List<String> categoriesIds = new ArrayList<>();

    public List<String> getCategoriesIds() {
        return categoriesIds;
    }

    public void setCategoriesIds(List<String> categoriesIds) {
        this.categoriesIds = categoriesIds;
    }

    public Double getPriceMoreThan() {
        return priceMoreThan;
    }

    public void setPriceMoreThan(Double priceMoreThan) {
        this.priceMoreThan = priceMoreThan;
    }

    public Double getPriceLessThan() {
        return priceLessThan;
    }

    public void setPriceLessThan(Double priceLessThan) {
        this.priceLessThan = priceLessThan;
    }
}
