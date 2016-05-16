package ru.nbakaev.hishop.customer;

/**
 *
 * For example
 * JSON {street: '123 Sesame St', city: 'Anytown', cc: 'USA' }
 *
 * Created by Nikita Bakaev, ya@nbakaev.ru on 5/16/2016.
 * All Rights Reserved
 */
public class Address {

    private String houseAddress;
    private String street;
    private String city;
    private String country;

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
