package com.ecom.b2cstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
public class PriceBook extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Getter
    @Setter
    private BigDecimal price;

    @Override
    public String toString() {
        return String.format("PriceBook[id=%d, productId=%s, price=%s]", id,
                product != null ? product.getProductId() : null,
                price);
    }
}
