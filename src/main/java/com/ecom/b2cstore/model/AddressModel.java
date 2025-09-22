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
    private boolean defaultAddress;

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
        this.defaultAddress = false;
    }

    public AddressModel(String firstName, String lastName, String email, String phone, String country, String address,
            String city, String state, String zipCode, boolean defaultAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.defaultAddress = defaultAddress;
    }

    public AddressModel(Address address) {
        copy(address);
    }

    public AddressModel(AddressEmbeddable address) {
        copy(address);
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
            this.defaultAddress = other.isDefaultAddress();
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
            this.defaultAddress = false;
        }
    }
}
