package com.example.demo.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private int houseNumber;
    private String street;
    private String city;
    private String county;
    private String postCode;

    // Геттеры и сеттеры
    public int getHouseNumber() { return houseNumber; }
    public void setHouseNumber(int houseNumber) { this.houseNumber = houseNumber; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCounty() { return county; }
    public void setCounty(String county) { this.county = county; }

    public String getPostCode() { return postCode; }
    public void setPostCode(String postCode) { this.postCode = postCode; }
}
