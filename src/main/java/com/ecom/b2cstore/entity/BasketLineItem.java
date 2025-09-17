package com.ecom.b2cstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class BasketLineItem extends LineItem {
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @Getter
    @Setter
    private String uuid;

    public OrderLineItem convertToOrderLineItem() {
        OrderLineItem orderItem = new OrderLineItem();
        orderItem.setProductId(getProductId());
        orderItem.setName(getName());
        orderItem.setAts(getAts());
        orderItem.setPrice(getPrice());
        return orderItem;
    }
}