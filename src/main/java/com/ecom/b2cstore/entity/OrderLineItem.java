package com.ecom.b2cstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class OrderLineItem extends LineItem {
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}