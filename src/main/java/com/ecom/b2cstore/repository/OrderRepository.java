package com.ecom.b2cstore.repository;

import com.ecom.b2cstore.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}