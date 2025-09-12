package com.ecom.b2cstore.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    @Autowired
    private ProductService productService;

    public Basket save(Basket basket) {
        return basketRepository.save(basket);
    }

    public Basket getBasketByGuestUUID(String guestUUID) {
        // Logic to retrieve the basket by guest UUID
        return basketRepository.findByGuestUUID(guestUUID).orElse(null);
    }

    public BasketLineItem createLineItem(Product product) {
        BasketLineItem lineItem = new BasketLineItem();
        lineItem.setProductId(product.getProductId());
        lineItem.setName(product.getName());
        lineItem.setPrice(product.getPrice());
        lineItem.setAts(1);
        return lineItem;
    }

    public Map<String, Product> getProductMap(Basket basket) {
        Set<String> productIds = basket.getLineItems().stream()
                .map(BasketLineItem::getProductId)
                .collect(java.util.stream.Collectors.toSet());
        Collection<Product> products = productService.getProductsByIds(productIds);

        // Convert Set to Map for easy lookup
        Map<String, Product> productMap = new HashMap<>();
        products.forEach(product -> productMap.put(product.getProductId(), product));
        return productMap;
    }

    public void saveShippingForm(Basket basket, ShippingForm form) {
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
