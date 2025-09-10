package com.ecom.b2cstore.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Product extends BaseEntity {

    @Id
    @Column(name = "product_id")
    @Getter
    @Setter
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

    @Override
    public String toString() {
        return String.format(
                "Product[id=%s, name='%s', online=%s, imageURL='%s', inventory=%s, priceBook=%s]",
                productId,
                name,
                online,
                imageURL,
                inventory != null ? inventory.toString() : "null",
                priceBook != null ? priceBook.toString() : "null");
    }

    public boolean isInStock() {
        return inventory != null && inventory.getAts() > 0;
    }

    public boolean hasPrice() {
        return priceBook != null && priceBook.getPrice() != null;
    }

    public BigDecimal getPrice() {
        return priceBook != null ? priceBook.getPrice() : null;
    }

    public Integer getAts() {
        return inventory != null ? inventory.getAts() : null;
    }
}
