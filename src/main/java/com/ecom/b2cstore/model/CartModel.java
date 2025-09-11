package com.ecom.b2cstore.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ecom.b2cstore.entity.Basket;
import com.ecom.b2cstore.entity.Product;

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

    @Getter
    @Setter
    private List<LineItemModel> items = new ArrayList<>();

    @Setter
    private Basket basket;

    public CartModel() {
        this.itemCount = 0;
        this.totalPrice = 0;
        this.totalTax = 0;
        this.totalDiscount = 0;
    }

    public CartModel(Basket basket) {
        if (basket != null) {
            this.basket = basket;
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

    public List<LineItemModel> createItemList(Map<String, Product> productMap) {
        List<LineItemModel> lineItemList = new ArrayList<>();
        if (basket != null && productMap != null) {
            basket.getLineItems().forEach(lineItem -> {
                Product product = productMap.get(lineItem.getProductId());
                if (product != null) {
                    LineItemModel lineItemModel = new LineItemModel(lineItem);
                    lineItemModel.parseProduct(product);
                    lineItemList.add(lineItemModel);
                }
            });
        }
        return lineItemList;
    }
}
