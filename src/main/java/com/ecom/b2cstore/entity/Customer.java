package com.ecom.b2cstore.entity;

import java.util.HashSet;
import java.util.Set;

import com.ecom.b2cstore.form.CustomerForm;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Getter
@Setter
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String country;

    @Column(unique = true)
    private String username;

    @NotBlank
    private String password;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Basket basket;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Address> addresses = new HashSet<>();

    public Customer() {
    }

    public Customer(String firstName, String lastName, String email, String username, String password, String phone,
            String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.country = country;
    }

    public Customer(CustomerForm form) {
        copy(form);
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s', email='%s', phone='%s', country='%s']",
                customerId, firstName, lastName, email, phone, country);
    }

    public void copy(CustomerForm form) {
        this.firstName = form.getFirstName();
        this.lastName = form.getLastName();
        this.email = form.getEmail();
        this.username = form.getUsername();
        this.password = form.getPassword();
        this.phone = form.getPhone();
        this.country = form.getCountry();
    }
}
