package com.ecom.b2cstore.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ecom.b2cstore.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByFirstName(String firstName);

    Customer findById(long id);
}
