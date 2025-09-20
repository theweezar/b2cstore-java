package com.ecom.b2cstore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String country;
    private String address;
    private String city;
    private String state;
    private String zipCode;

    public Address() {
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

    public Address(String firstName, String lastName, String email, String phone, String country, String address,
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

    public String toString() {
        return String.format(
                "Address[id=%d, firstName=%s, lastName=%s, email=%s, phone=%s, country=%s, address=%s, city=%s, state=%s, zipCode=%s]",
                getId(), firstName, lastName, email, phone, country, address, city, state, zipCode);
    }
}
