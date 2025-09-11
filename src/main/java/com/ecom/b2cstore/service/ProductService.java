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

    public ProductStatus getProductStatus(String productId) {
        Product product = productRepository.findByProductId(productId).orElse(null);

        if (product == null) {
            return new ProductStatus(null, ProductStatus.NOT_FOUND);
        } else if (!product.isOnline()) {
            return new ProductStatus(product, ProductStatus.OFFLINE);
        } else if (!product.hasPrice()) {
            return new ProductStatus(product, ProductStatus.PRICE_NA);
        } else if (!product.isInStock()) {
            return new ProductStatus(product, ProductStatus.OUT_OF_STOCK);
        }

        return new ProductStatus(product, ProductStatus.VALID);
    }

    public Collection<Product> getProductsByIds(Collection<String> productIds) {
        return productRepository.findAllByProductIdIn(productIds);
    }
}
