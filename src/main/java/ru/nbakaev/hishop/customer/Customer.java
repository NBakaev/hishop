package ru.nbakaev.hishop.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 5/16/2016.
 * All Rights Reserved
 */
@ApiModel("Customer data, which user can free modify")
public class Customer {

    @ApiModelProperty("Customer firstname")
    private String firstname;

    @ApiModelProperty("Customer lastname")
    private String lastname;

    @ApiModelProperty("Customer patronymic")
    private String patronymic;

    @ApiModelProperty("Customer primary address")
    private Address primaryAddress = new Address();


    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
}
