package com.medicalsuppliesmanagement.service;

import com.medicalsuppliesmanagement.entity.Customer;

public interface ICustomerService {
    Customer save(Customer customer);
    Customer findById(Long id);
} 