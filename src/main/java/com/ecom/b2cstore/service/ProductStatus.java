package com.ecom.b2cstore.service;

import com.ecom.b2cstore.entity.Product;
import lombok.Getter;
import lombok.Setter;

public class ProductStatus {
    public static final String NOT_FOUND = "NOT_FOUND";
    public static final String IN_STOCK = "IN_STOCK";
    public static final String OUT_OF_STOCK = "OUT_OF_STOCK";
    public static final String ONLINE = "ONLINE";
    public static final String OFFLINE = "OFFLINE";
    public static final String PRICE_NA = "PRICE_NA";
    public static final String VALID = "VALID";

    @Getter
    @Setter
    private Product product;

    @Getter
    @Setter
    private String status;

    public ProductStatus() {
        this.status = NOT_FOUND;
    }

    public ProductStatus(Product product, String status) {
        this.product = product;
        this.status = status;
    }
}
