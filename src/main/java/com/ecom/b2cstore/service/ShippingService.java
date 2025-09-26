package com.ecom.b2cstore.service;

import com.ecom.b2cstore.entity.ShippingMethod;
import com.ecom.b2cstore.repository.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShippingService {
    @Autowired
    private ShippingRepository shippingRepository;

    public void save(ShippingMethod shipping) {
        shippingRepository.save(shipping);
    }
}
