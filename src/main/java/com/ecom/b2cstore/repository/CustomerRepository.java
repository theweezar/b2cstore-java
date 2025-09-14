package com.ecom.b2cstore.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.ecom.b2cstore.entity.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findById(long id);

    Optional<Customer> findByUsername(String username);

    Optional<Customer> findByEmail(String email);

}
