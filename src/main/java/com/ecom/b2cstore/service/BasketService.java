package com.ecom.b2cstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecom.b2cstore.entity.Basket;
import com.ecom.b2cstore.entity.BasketLineItem;
import com.ecom.b2cstore.entity.Product;
import com.ecom.b2cstore.repository.BasketRepository;

@Service
public class BasketService {
    @Autowired
    private BasketRepository basketRepository;

    public Basket getBasketByGuestUUID(String guestUUID) {
        // Logic to retrieve the basket by guest UUID
        return basketRepository.findByGuestUUID(guestUUID).orElse(null);
    }

    public BasketLineItem createLineItem(Product product) {
        BasketLineItem lineItem = new BasketLineItem();
        lineItem.setName(product.getName());
        lineItem.setPrice(product.getPrice());
        lineItem.setAts(1);
        return lineItem;
    }

    public Basket save(Basket basket) {
        return basketRepository.save(basket);
    }
}
