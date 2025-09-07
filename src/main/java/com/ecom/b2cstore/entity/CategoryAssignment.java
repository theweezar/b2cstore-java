package com.ecom.b2cstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class CategoryAssignment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    // Many assignments belong to one category
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @Getter
    @Setter
    private Category category;

    // Many assignments belong to one product
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @Getter
    @Setter
    private Product product;

    public CategoryAssignment() {
    }

    public CategoryAssignment(Category category, Product product) {
        this.category = category;
        this.product = product;
    }

}
