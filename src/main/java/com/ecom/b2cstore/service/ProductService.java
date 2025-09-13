package com.ecom.b2cstore.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecom.b2cstore.entity.Product;
import com.ecom.b2cstore.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product getProductById(String productId) {
        return productRepository.findByProductId(productId).orElse(null);
    }

    public Collection<Product> getProductsByIds(Collection<String> productIds) {
        return productRepository.findAllByProductIdIn(productIds);
    }
}
