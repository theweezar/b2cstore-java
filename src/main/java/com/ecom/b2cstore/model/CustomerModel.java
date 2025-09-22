package com.ecom.b2cstore.model;

import java.util.HashSet;
import java.util.Set;

import com.ecom.b2cstore.entity.Address;
import com.ecom.b2cstore.entity.Customer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerModel {
    private Long customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String phone;
    private String country;
    private Set<AddressModel> addresses = new HashSet<>();

    public CustomerModel() {
        this.customerId = null;
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.username = "";
        this.phone = "";
        this.country = "";
    }

    public CustomerModel(Long customerId, String firstName, String lastName, String email, String username,
            String phone, String country) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.country = country;
    }

    public CustomerModel(Customer customer) {
        this.customerId = customer.getCustomerId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.email = customer.getEmail();
        this.username = customer.getUsername();
        this.phone = customer.getPhone();
        this.country = customer.getCountry();
        this.addresses = new HashSet<>();

        Set<Address> addressEntities = customer.getAddresses();

        if (addressEntities.size() > 0) {
            for (var addr : addressEntities) {
                this.addresses.add(new AddressModel(addr));
            }
        }
    }
}
