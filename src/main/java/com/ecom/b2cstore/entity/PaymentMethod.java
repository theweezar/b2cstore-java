package com.ecom.b2cstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PaymentMethod extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    private String name;

    private boolean enable;

    private String imageURL;

    @Override
    public String toString() {
        return String.format("PaymentMethod[id=%d, code=%s, name=%s, enable=%b, imageURL=%s]", id, code, name, enable,
                imageURL);
    }

    public PaymentMethod() {
    }

    public PaymentMethod(String code, String name, boolean enable, String imageURL) {
        this.code = code;
        this.name = name;
        this.enable = enable;
        this.imageURL = imageURL;
    }
}
