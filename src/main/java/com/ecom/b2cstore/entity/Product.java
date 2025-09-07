package com.ecom.b2cstore.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Product extends BaseEntity {

    @Id
    @Column(name = "product_id")
    @Getter
    private String productId;

    @Getter
    @Setter
    private String name;

    @Column(length = 2000)
    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private boolean online;

    @Getter
    @Setter
    private String imageURL;

    @Getter
    @Setter
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Inventory inventory;

    @Getter
    @Setter
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private PriceBook priceBook;

    @Getter
    @Setter
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CategoryAssignment> assignments = new HashSet<>();

    public Product() {
    }

    public Product(String id, String name, String description, boolean online, String imageURL) {
        this.productId = id;
        this.name = name;
        this.description = description;
        this.online = online;
        this.imageURL = imageURL;
    }

    public void setProductId(String id) {
        this.productId = id;
    }

    @Override
    public String toString() {
        return String.format("Product[id=%s, name='%s', online=%s, imageURL='%s']", productId, name, online, imageURL);
    }
}
