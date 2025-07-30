package com.medicalsuppliesmanagement.service;


import com.medicalsuppliesmanagement.dto.CustomerDto;
import com.medicalsuppliesmanagement.entity.Customer;

public interface ICustomerService {
    Customer save(Customer customer);
    Customer findById(Long id);
    CustomerDto getCustomerProfile(String username);
    void updateCustomer(CustomerDto dto);
}