package com.ecom.b2cstore.repository;

import com.ecom.b2cstore.entity.Basket;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface BasketRepository extends CrudRepository<Basket, Long> {
    Optional<Basket> findByGuestUUID(String guestUUID);

    Optional<Basket> findByCustomerCustomerId(Long customerId);
}