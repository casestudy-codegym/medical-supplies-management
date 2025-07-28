package com.medicalsuppliesmanagement.service.impl;

import com.medicalsuppliesmanagement.entity.Customer;
import com.medicalsuppliesmanagement.repository.ICustomerRepository;
import com.medicalsuppliesmanagement.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
} 