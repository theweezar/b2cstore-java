package com.ecom.b2cstore.model;

import com.ecom.b2cstore.entity.Address;
import com.ecom.b2cstore.entity.AddressEmbeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressModel {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String country;
    private String address;
    private String city;
    private String state;
    private String zipCode;

    public AddressModel() {
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.phone = "";
        this.country = "";
        this.address = "";
        this.city = "";
        this.state = "";
        this.zipCode = "";
    }

    public AddressModel(String firstName, String lastName, String email, String phone, String country, String address,
            String city, String state, String zipCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public void copy(Address other) {
        if (other != null) {
            this.firstName = other.getFirstName();
            this.lastName = other.getLastName();
            this.email = other.getEmail();
            this.phone = other.getPhone();
            this.country = other.getCountry();
            this.address = other.getAddress();
            this.city = other.getCity();
            this.state = other.getState();
            this.zipCode = other.getZipCode();
        }
    }

    public void copy(AddressEmbeddable other) {
        if (other != null) {
            this.firstName = other.getFirstName();
            this.lastName = other.getLastName();
            this.email = other.getEmail();
            this.phone = other.getPhone();
            this.country = other.getCountry();
            this.address = other.getAddress();
            this.city = other.getCity();
            this.state = other.getState();
            this.zipCode = other.getZipCode();
        }
    }
}
