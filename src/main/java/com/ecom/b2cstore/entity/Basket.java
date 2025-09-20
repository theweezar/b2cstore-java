package com.ecom.b2cstore.entity;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Basket extends Container {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    @Setter
    private Set<BasketLineItem> lineItems = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "customer_id", unique = true)
    @Getter
    @Setter
    private Customer customer;

    @Getter
    @Setter
    @Column(name = "guest_uuid", unique = true)
    private String guestUUID;

    public Basket() {
        super();
    }

    @Override
    public Set<? extends LineItem> getContainerLineItems() {
        return lineItems;
    }

    public Order convertToOrder() {
        Order order = new Order();
        for (BasketLineItem basketItem : this.lineItems) {
            OrderLineItem orderItem = basketItem.convertToOrderLineItem();
            orderItem.setOrder(order);
            order.getLineItems().add(orderItem);
        }
        order.setStatus(Order.STATUS_CREATED);
        order.setConfirmationStatus(Order.CONFIRMATION_STATUS_UNCONFIRMED);
        order.setPaymentStatus(Order.PAYMENT_STATUS_UNPAID);
        order.setShippingStatus(Order.SHIPPING_STATUS_NOT_SHIPPED);

        BeanUtils.copyProperties(this, order, "id", "lineItems", "customer", "guestUUID");

        return order;
    }

    public double getTotalPrice() {
        return lineItems.stream()
                .mapToDouble(li -> li.getPrice().doubleValue() * li.getAts())
                .sum();
    }
}