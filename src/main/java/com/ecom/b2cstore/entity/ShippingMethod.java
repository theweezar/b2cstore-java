package com.ecom.b2cstore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ShippingMethod extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String code;

    private String name;

    private String description;

    private boolean enable;

    private double price;

    @Override
    public String toString() {
        return String.format("ShippingMethod[id=%d, code=%s, name=%s, description=%s, price=%.2f]", id, code, name, description, price);
    }

    public ShippingMethod() {
    }

    public ShippingMethod(String code, String name, String description, boolean enable, double price) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.enable = enable;
        this.price = price;
    }
}
