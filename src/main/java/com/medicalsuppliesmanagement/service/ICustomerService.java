package com.medicalsuppliesmanagement.service;

import com.medicalsuppliesmanagement.entity.Customer;
import com.medicalsuppliesmanagement.repository.ICustomerRepository;
import com.medicalsuppliesmanagement.service.impl.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ICustomerService implements CustomerService {

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

    @Override
    public Page<Customer> findByNameContainingIgnoreCase(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return customerRepository.findAll(pageable);
        }
        return customerRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Customer update(Customer customer) {
        return customerRepository.findById(customer.getCustomerId())
                .map(existingCustomer -> {
                    existingCustomer.setCustomerCode(customer.getCustomerCode());
                    existingCustomer.setName(customer.getName());
                    existingCustomer.setAddress(customer.getAddress());
                    existingCustomer.setPhone(customer.getPhone());
                    existingCustomer.setEmail(customer.getEmail());
                    existingCustomer.setType(customer.getType()); // Cập nhật loại khách hàng
                    return customerRepository.save(existingCustomer);
                })
                .orElse(null);
    }

    @Override
    public Optional<Customer> findByCustomerCode(String code) {
        return customerRepository.findByCustomerCode(code);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        for (Long id : ids) {
            customerRepository.deleteById(id);
        }
    }
}