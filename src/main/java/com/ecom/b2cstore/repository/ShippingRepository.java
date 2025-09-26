package com.ecom.b2cstore.repository;

import com.ecom.b2cstore.entity.ShippingMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingRepository extends JpaRepository<ShippingMethod, Long> {
}
