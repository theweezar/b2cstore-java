package com.ecom.b2cstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecom.b2cstore.entity.Basket;
import com.ecom.b2cstore.entity.BasketLineItem;
import com.ecom.b2cstore.entity.Product;
import com.ecom.b2cstore.form.BillingForm;
import com.ecom.b2cstore.form.ShippingForm;
import com.ecom.b2cstore.repository.BasketRepository;
import com.ecom.b2cstore.util.UUIDUtil;

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
        lineItem.setUuid(UUIDUtil.generateNameUUIDFromCurrentTime().toString());
        return lineItem;
    }

    public void setCustomerInfo(Basket basket, ShippingForm form) {
        basket.setFirstName(form.getFirstName());
        basket.setLastName(form.getLastName());
        basket.setEmail(form.getEmail());
        basket.setPhone(form.getPhone());
    }

    public void setShippingAddress(Basket basket, ShippingForm form) {
        basket.getShippingAddress().setFirstName(form.getShippingAddress().getFirstName());
        basket.getShippingAddress().setLastName(form.getShippingAddress().getLastName());
        basket.getShippingAddress().setEmail(form.getShippingAddress().getEmail());
        basket.getShippingAddress().setPhone(form.getShippingAddress().getPhone());
        basket.getShippingAddress().setAddress(form.getShippingAddress().getAddress());
        basket.getShippingAddress().setCity(form.getShippingAddress().getCity());
        basket.getShippingAddress().setState(form.getShippingAddress().getState());
        basket.getShippingAddress().setCountry(form.getShippingAddress().getCountry());
        basket.getShippingAddress().setZipCode(form.getShippingAddress().getZipCode());
    }

    public void setBillingAddress(Basket basket, BillingForm form) {
        basket.getBillingAddress().setFirstName(form.getBillingAddress().getFirstName());
        basket.getBillingAddress().setLastName(form.getBillingAddress().getLastName());
        basket.getBillingAddress().setEmail(form.getBillingAddress().getEmail());
        basket.getBillingAddress().setPhone(form.getBillingAddress().getPhone());
        basket.getBillingAddress().setAddress(form.getBillingAddress().getAddress());
        basket.getBillingAddress().setCity(form.getBillingAddress().getCity());
        basket.getBillingAddress().setState(form.getBillingAddress().getState());
        basket.getBillingAddress().setCountry(form.getBillingAddress().getCountry());
        basket.getBillingAddress().setZipCode(form.getBillingAddress().getZipCode());
    }
}
