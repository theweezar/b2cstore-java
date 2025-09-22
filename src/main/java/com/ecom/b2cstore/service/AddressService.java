package com.ecom.b2cstore.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecom.b2cstore.entity.Address;
import com.ecom.b2cstore.entity.Customer;
import com.ecom.b2cstore.repository.AddressRepository;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address create(Address address) {
        return addressRepository.save(address);
    }

    public Address update(Address address) {
        return addressRepository.save(address);
    }

    public void delete(Long id) {
        addressRepository.deleteById(id);
    }

    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public Optional<Address> findById(Long id) {
        return addressRepository.findById(id);
    }

    public List<Address> findByCustomer(Customer customer) {
        return addressRepository.findByCustomer(customer);
    }

    public List<Address> findByCustomerId(Long customerId) {
        return addressRepository.findByCustomerCustomerId(customerId);
    }

    public Address findByIdAndCustomerId(Long addressId, Long customerId) {
        return addressRepository.findByIdAndCustomerCustomerId(addressId, customerId).orElse(null);
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }
}