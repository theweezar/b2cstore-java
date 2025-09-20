package com.ecom.b2cstore.entity;

import org.springframework.beans.BeanUtils;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class AddressEmbeddable {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String country;
    private String address;
    private String city;
    private String state;
    private String zipCode;

    public AddressEmbeddable() {
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

    public AddressEmbeddable(String firstName, String lastName, String email, String phone, String country,
            String address,
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

    public void copy(AddressEmbeddable other) {
        BeanUtils.copyProperties(other, this);
    }

    public void copy(Address addressEntity) {
        BeanUtils.copyProperties(addressEntity, this, "id");
    }

    @Override
    public String toString() {
        return String.format(
                "AddressEmbeddable[firstName=%s, lastName=%s, email=%s, phone=%s, country=%s, address=%s, city=%s, state=%s, zipCode=%s]",
                firstName, lastName, email, phone, country, address, city, state, zipCode);
    }
}
