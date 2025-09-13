package com.ecom.b2cstore.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.ecom.b2cstore.entity.Inventory;

public interface InventoryRepository extends CrudRepository<Inventory, Long> {
    @Query("SELECT i FROM Inventory i WHERE i.product.productId IN :productIds")
    Collection<Inventory> findAllByProductIdIn(@Param("productIds") Iterable<String> productIds);

    Optional<Inventory> findByProduct_ProductId(String productId);
}
