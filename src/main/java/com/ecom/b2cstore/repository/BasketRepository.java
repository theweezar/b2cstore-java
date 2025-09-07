package com.ecom.b2cstore.repository;

import com.ecom.b2cstore.entity.Basket;
import org.springframework.data.repository.CrudRepository;

public interface BasketRepository extends CrudRepository<Basket, Long> {
}