package com.ecom.b2cstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ecom.b2cstore.entity.Address;
import com.ecom.b2cstore.entity.Customer;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByCustomer(Customer customer);

    List<Address> findByCustomerCustomerId(Long customerId);

    Optional<Address> findByIdAndCustomerCustomerId(Long addressId, Long customerId);
}