package com.ecom.b2cstore.model;

import java.time.LocalDateTime;

import com.ecom.b2cstore.entity.Order;

import lombok.Getter;
import lombok.Setter;

public class OrderModel extends ContainerModel {

    @Setter
    private Order order;

    @Getter
    @Setter
    private Long orderId;
    
    @Getter
    @Setter
    private LocalDateTime creationDate;

    public OrderModel() {
        super();
    }

    public OrderModel(Order order) {
        super();
        if (order != null) {
            setOrder(order);
            setOrderId(order.getOrderId());
            setCreationDate(order.getCreationDate());
            setItemCount(order.getLineItems().size());
            total.setTotalPrice(order.getLineItems().stream()
                    .mapToDouble(li -> li.getPrice().doubleValue() * li.getAts())
                    .sum());
            total.setTotalTax(0);
            total.setTotalDiscount(0);
            total.setTotalShipping(0);
            copyShippingFrom(order);
            copyBillingFrom(order);
        }
    }

    @Override
    protected Order getContainerInstance() {
        return order;
    }
}
