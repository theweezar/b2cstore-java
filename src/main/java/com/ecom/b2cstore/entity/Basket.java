package com.ecom.b2cstore.entity;

import java.util.HashSet;
import java.util.Set;
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
}