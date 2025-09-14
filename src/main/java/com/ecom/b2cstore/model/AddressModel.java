package com.ecom.b2cstore.model;

import com.ecom.b2cstore.entity.Container;
import lombok.Getter;
import lombok.Setter;

public class AddressModel {
    @Getter
    @Setter
    private String country;

    @Getter
    @Setter
    private String address;

    @Getter
    @Setter
    private String city;

    @Getter
    @Setter
    private String state;

    @Getter
    @Setter
    private String zipCode;

    public AddressModel() {
        this.country = "";
        this.address = "";
        this.city = "";
        this.state = "";
        this.zipCode = "";
    }

    public AddressModel(String country, String address, String city, String state, String zipCode) {
        this.country = country;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public void copy(Container container) {
        if (container != null) {
            this.country = container.getCountry();
            this.address = container.getAddress();
            this.city = container.getCity();
            this.state = container.getState();
            this.zipCode = container.getZipCode();
        }
    }
}
