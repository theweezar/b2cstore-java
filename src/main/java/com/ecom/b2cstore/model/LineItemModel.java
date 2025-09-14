package com.ecom.b2cstore.model;

import java.math.BigDecimal;
import com.ecom.b2cstore.entity.LineItem;
import com.ecom.b2cstore.entity.Product;
import lombok.Getter;
import lombok.Setter;

public class LineItemModel {
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

    @Getter
    @Setter
    private String imageURL;

    public LineItemModel() {
        this.productId = "";
        this.name = "";
        this.price = BigDecimal.ZERO;
        this.ats = 0;
    }

    public LineItemModel(LineItem lineItem) {
        this.productId = lineItem.getProductId();
        this.name = lineItem.getName();
        this.price = lineItem.getPrice();
        this.ats = lineItem.getAts();
    }

    public void parseProduct(Product product) {
        if (product != null) {
            this.imageURL = product.getImageURL();
        }
    }
}
