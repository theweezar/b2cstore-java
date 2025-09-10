package com.ecom.b2cstore.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.ecom.b2cstore.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {
    Optional<Product> findByProductId(String productId);

    // Iterable<Product> findAllByProductIds(Iterable<String> productIds);
}
