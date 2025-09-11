package com.ecom.b2cstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class LineItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(name = "product_id")
    @Getter
    @Setter
    private String productId;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private BigDecimal price;

    @Getter
    @Setter
    private int ats;

    @Override
    public String toString() {
        return String.format(
                "%s[id=%d, productId='%s', name='%s', price='%s', ats='%d']",
                this.getClass().getSimpleName(), id, productId, name, price, ats);
    }
}
