package com.ecom.b2cstore.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders") // "order" is a reserved keyword in SQL
public class Order extends Container {
    @Id
    @Column(name = "order_id")
    @Getter
    @Setter
    private String orderId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    @Setter
    private Set<OrderLineItem> lineItems = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @Getter
    @Setter
    private Customer customer;
}