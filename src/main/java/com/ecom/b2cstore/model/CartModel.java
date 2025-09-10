package com.ecom.b2cstore.model;

import com.ecom.b2cstore.entity.Basket;
import lombok.Getter;
import lombok.Setter;

public class CartModel {
    @Getter
    @Setter
    private int itemCount;

    @Getter
    @Setter
    private double totalPrice;

    @Getter
    @Setter
    private double totalTax;

    @Getter
    @Setter
    private double totalDiscount;

    public CartModel(Basket basket) {
        if (basket != null) {
            this.itemCount = basket.getLineItems().size();
            this.totalPrice = basket.getLineItems().stream()
                    .mapToDouble(li -> li.getPrice().doubleValue() * li.getAts())
                    .sum();
            this.totalTax = 0;
            this.totalDiscount = 0;
        } else {
            this.itemCount = 0;
            this.totalPrice = 0;
            this.totalTax = 0;
            this.totalDiscount = 0;
        }
    }
}
