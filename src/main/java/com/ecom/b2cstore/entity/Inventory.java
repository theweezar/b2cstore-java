package com.ecom.b2cstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Inventory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    @Getter
    @Setter
    private Product product;

    @Getter
    @Setter
    private int ats;

    public Inventory() {
    }

    public Inventory(Product product, int ats) {
        this.product = product;
        this.ats = ats;
    }

    @Override
    public String toString() {
        return String.format("Inventory[id=%d, productId=%s, ats=%d]", id,
                product != null ? product.getProductId() : null,
                ats);
    }
}
