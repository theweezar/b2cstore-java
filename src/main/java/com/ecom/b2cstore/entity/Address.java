package com.ecom.b2cstore.entity;

import com.ecom.b2cstore.form.AddressForm;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private boolean defaultAddress;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

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
        this.defaultAddress = false;
    }

    public Address(String firstName, String lastName, String email, String phone, String country, String address,
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

    public Address(AddressForm form) {
        copy(form);
    }

    public String toString() {
        return String.format(
                "Address[id=%d, firstName=%s, lastName=%s, email=%s, phone=%s, country=%s, address=%s, city=%s, state=%s, zipCode=%s, defaultAddress=%s]",
                getId(), firstName, lastName, email, phone, country, address, city, state, zipCode, defaultAddress);
    }

    public void copy(AddressForm form) {
        this.firstName = form.getFirstName();
        this.lastName = form.getLastName();
        this.email = form.getEmail();
        this.phone = form.getPhone();
        this.country = form.getCountry();
        this.address = form.getAddress();
        this.city = form.getCity();
        this.state = form.getState();
        this.zipCode = form.getZipCode();
        this.defaultAddress = form.isDefaultAddress();
    }
}
