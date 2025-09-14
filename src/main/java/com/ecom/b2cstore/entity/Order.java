package com.ecom.b2cstore.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders") // "order" is a reserved keyword in SQL
public class Order extends Container {

    public static final int STATUS_CREATED = 0;
    public static final int STATUS_OPEN = 1;
    public static final int STATUS_COMPLETED = 2;
    public static final int STATUS_CANCELLED = 3;
    public static final int STATUS_FAILED = 4;

    public static final int CONFIRMATION_STATUS_UNCONFIRMED = 0;
    public static final int CONFIRMATION_STATUS_CONFIRMED = 1;

    public static final int PAYMENT_STATUS_UNPAID = 0;
    public static final int PAYMENT_STATUS_PAID = 1;

    public static final int SHIPPING_STATUS_NOT_SHIPPED = 0;
    public static final int SHIPPING_STATUS_SHIPPED = 1;

    @Id
    @Column(name = "order_id", unique = true)
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    @Setter
    private Set<OrderLineItem> lineItems = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @Getter
    @Setter
    private Customer customer;

    @Getter
    @Setter
    private int status; // e.g., 0 = pending, 1 = completed, 2 = shipped, etc.

    @Getter
    @Setter
    private int confirmationStatus; // e.g., 0 = unconfirmed, 1 = confirmed, etc.

    @Getter
    @Setter
    private int paymentStatus; // e.g., 0 = unpaid, 1 = paid, 2 = refunded, etc.

    @Getter
    @Setter
    private int shippingStatus; // e.g., 0 = not shipped, 1 = shipped, 2 = delivered, etc.

    public Order() {
    }

    @Override
    public Set<? extends LineItem> getContainerLineItems() {
        return lineItems;
    }

    @Override
    public String toString() {
        return String.format(
                "Order[id=%d, customerId=%s, status=%d, confirmationStatus=%d, paymentStatus=%d, shippingStatus=%d, firstName=%s, lastName=%s, email=%s, phone=%s, shipFirstName=%s, shipLastName=%s, country=%s, address=%s, city=%s, state=%s, zipCode=%s, lineItems=%d]",
                orderId,
                customer != null ? customer.getCustomerId() : null,
                status,
                confirmationStatus,
                paymentStatus,
                shippingStatus,
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
                getZipCode(),
                lineItems != null ? lineItems.size() : 0);
    }
}