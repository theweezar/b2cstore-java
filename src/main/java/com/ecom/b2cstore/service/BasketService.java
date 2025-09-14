package com.ecom.b2cstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecom.b2cstore.entity.Basket;
import com.ecom.b2cstore.entity.BasketLineItem;
import com.ecom.b2cstore.entity.Product;
import com.ecom.b2cstore.form.ShippingForm;
import com.ecom.b2cstore.repository.BasketRepository;

@Service
public class BasketService {
    @Autowired
    private BasketRepository basketRepository;

    public Basket save(Basket basket) {
        return basketRepository.save(basket);
    }

    public void deleteBasket(Basket basket) {
        if (basket != null && basket.getId() != null) {
            basketRepository.delete(basket);
        }
    }

    public Basket getBasketByGuestUUID(String guestUUID) {
        return basketRepository.findByGuestUUID(guestUUID).orElse(null);
    }

    public Basket getBasketByCustomerId(Long customerId) {
        return basketRepository.findByCustomerCustomerId(customerId).orElse(null);
    }

    public BasketLineItem createLineItem(Product product) {
        BasketLineItem lineItem = new BasketLineItem();
        lineItem.setProductId(product.getProductId());
        lineItem.setName(product.getName());
        lineItem.setPrice(product.getPrice());
        lineItem.setAts(1);
        return lineItem;
    }

    public void saveShippingBillingForm(Basket basket, ShippingForm form) {
        basket.setFirstName(form.getFirstName());
        basket.setLastName(form.getLastName());
        basket.setEmail(form.getEmail());
        basket.setPhone(form.getPhone());
        basket.setShipFirstName(form.getShipFirstName());
        basket.setShipLastName(form.getShipLastName());
        basket.setAddress(form.getAddress());
        basket.setCity(form.getCity());
        basket.setState(form.getState());
        basket.setCountry(form.getCountry());
        basket.setZipCode(form.getZipCode());
        save(basket);
    }
}
