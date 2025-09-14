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

    @Override
    public Set<? extends LineItem> getContainerLineItems() {
        return lineItems;
    }

    @Override
    public String toString() {
        return String.format(
                "Basket[id=%d, customerId=%s, guestUUID=%s, lineItems=%d, firstName=%s, lastName=%s, email=%s, phone=%s, shipFirstName=%s, shipLastName=%s, country=%s, address=%s, city=%s, state=%s, zipCode=%s]",
                id,
                customer != null ? customer.getCustomerId() : null,
                guestUUID,
                lineItems != null ? lineItems.size() : 0,
                getFirstName(),
                getLastName(),
                getEmail(),
                getPhone(),
                getShipFirstName(),
                getShipLastName(),
                getCountry(),
                getAddress(),
                getCity(),
                getState(),
                getZipCode());
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

        // Copy properties from Container
        // order.setFirstName(getFirstName());
        // order.setLastName(getLastName());
        // order.setEmail(getEmail());
        // order.setPhone(getPhone());
        // order.setShipFirstName(getShipFirstName());
        // order.setShipLastName(getShipLastName());
        // order.setCountry(getCountry());
        // order.setAddress(getAddress());
        // order.setCity(getCity());
        // order.setState(getState());
        // order.setZipCode(getZipCode());

        // Use a utility like BeanUtils to copy properties from Container
        BeanUtils.copyProperties(this, order, "id", "lineItems", "customer", "guestUUID");

        return order;
    }
}