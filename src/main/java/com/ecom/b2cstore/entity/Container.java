package com.ecom.b2cstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public abstract class Container extends BaseEntity {
    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String phone;

    @Getter
    @Setter
    private String country;

    @Getter
    @Setter
    private String address;

    public Container() {
    }

    @Override
    public String toString() {
        return String.format(
                "%s[id=%d, firstName='%s', lastName='%s', email='%s', phone='%s', country='%s', address='%s']",
                this.getClass().getSimpleName(), firstName, lastName, email, phone, country, address);
    }
}
