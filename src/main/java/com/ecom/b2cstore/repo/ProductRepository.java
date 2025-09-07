package com.ecom.b2cstore.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecom.b2cstore.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {
    Product findByProductId(String productId);
}
