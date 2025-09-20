package com.ecom.b2cstore.entity;

import java.util.Set;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class Container extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "shipping_first_name")),
            @AttributeOverride(name = "lastName", column = @Column(name = "shipping_last_name")),
            @AttributeOverride(name = "email", column = @Column(name = "shipping_email")),
            @AttributeOverride(name = "phone", column = @Column(name = "shipping_phone")),
            @AttributeOverride(name = "country", column = @Column(name = "shipping_country")),
            @AttributeOverride(name = "address", column = @Column(name = "shipping_address")),
            @AttributeOverride(name = "city", column = @Column(name = "shipping_city")),
            @AttributeOverride(name = "state", column = @Column(name = "shipping_state")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "shipping_zip_code"))
    })
    private AddressEmbeddable shippingAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "billing_first_name")),
            @AttributeOverride(name = "lastName", column = @Column(name = "billing_last_name")),
            @AttributeOverride(name = "email", column = @Column(name = "billing_email")),
            @AttributeOverride(name = "phone", column = @Column(name = "billing_phone")),
            @AttributeOverride(name = "country", column = @Column(name = "billing_country")),
            @AttributeOverride(name = "address", column = @Column(name = "billing_address")),
            @AttributeOverride(name = "city", column = @Column(name = "billing_city")),
            @AttributeOverride(name = "state", column = @Column(name = "billing_state")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "billing_zip_code"))
    })
    private AddressEmbeddable billingAddress;

    public Container() {
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.phone = "";
        this.shippingAddress = new AddressEmbeddable();
        this.billingAddress = new AddressEmbeddable();
    }

    public abstract Set<? extends LineItem> getContainerLineItems();
}
