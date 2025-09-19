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

    public static final int STATUS_NOT_FOUND = 0;
    public static final int STATUS_IN_STOCK = 1;
    public static final int STATUS_OUT_OF_STOCK = 2;
    public static final int STATUS_ONLINE = 3;
    public static final int STATUS_OFFLINE = 4;
    public static final int STATUS_PRICE_NA = 5;
    public static final int STATUS_VALID = 6;

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
    private int rating;

    @Getter
    @Setter
    private int reviewCount;

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

    public int getStatus() {
        if (!isOnline()) {
            return STATUS_OFFLINE;
        } else if (!hasPrice()) {
            return STATUS_PRICE_NA;
        } else if (!isInStock()) {
            return STATUS_OUT_OF_STOCK;
        }
        return STATUS_VALID;
    }
}
