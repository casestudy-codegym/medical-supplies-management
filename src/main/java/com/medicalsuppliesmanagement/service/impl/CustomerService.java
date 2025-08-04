package com.medicalsuppliesmanagement.service.impl;


import com.medicalsuppliesmanagement.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer save(Customer customer);
    Customer findById(Long id);
    Page<Customer> findByNameContainingIgnoreCase(String keyword,Pageable pageable);
    void deleteById(Long id);
    Customer update(Customer customer);
    Optional<Customer> findByCustomerCode(String code);
    Page<Customer> searchCustomers(String keyword, String type, int page, int size);
    void deleteByIds(List<Long> ids);
    List<Customer> getAllCustomers();
    long countCustomers();
} 