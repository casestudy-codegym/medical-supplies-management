package com.medicalsuppliesmanagement.service.impl;

import com.medicalsuppliesmanagement.dto.CustomerDto;
import com.medicalsuppliesmanagement.entity.Customer;
import com.medicalsuppliesmanagement.repository.ICustomerRepository;
import com.medicalsuppliesmanagement.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public CustomerDto getCustomerProfile(String username) {
        Customer customer = customerRepository.findByUser_Username(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        CustomerDto dto = new CustomerDto();
        dto.setUsername(customer.getUser().getUsername());
        dto.setName(customer.getUser().getFullName());

        String avatarUrl = customer.getUser().getAvatarUrl();
        if (avatarUrl == null || avatarUrl.isBlank()) {
            dto.setAvatarUrl("https://www.w3schools.com/howto/img_avatar.png");
        } else {
            dto.setAvatarUrl(avatarUrl);
        }

        dto.setCustomerCode(customer.getCustomerCode());
        dto.setPhone(customer.getPhone());
        dto.setEmail(customer.getEmail());
        dto.setAddress(customer.getAddress());
        dto.setType(customer.getType());

        return dto;
    }

    @Override
    public void updateCustomer(CustomerDto dto) {
        Customer customer = customerRepository.findByUser_Username(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setPhone(dto.getPhone());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());
        customer.setType(dto.getType());

        customer.getUser().setFullName(dto.getName());
        customer.getUser().setAvatarUrl(dto.getAvatarUrl());

        customerRepository.save(customer);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
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
                    existingCustomer.setType(customer.getType());
                    return customerRepository.save(existingCustomer);
                })
                .orElse(null);
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public Optional<Customer> findByCustomerCode(String code) {
        return customerRepository.findByCustomerCode(code);
    }

    @Override
    public Page<Customer> findByNameContainingIgnoreCase(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return customerRepository.findAll(pageable);
        }
        return customerRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }

    @Override
    public Page<Customer> searchCustomers(String keyword, String type, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);

        if ((keyword == null || keyword.isBlank()) && (type == null || type.isBlank())) {
            return customerRepository.findAll(pageable);
        } else if (type != null && !type.isBlank() && (keyword == null || keyword.isBlank())) {
            return customerRepository.findByType(Customer.CustomerType.valueOf(type), pageable);
        } else if (type == null || type.isBlank()) {
            return customerRepository.findByNameContainingIgnoreCase(keyword, pageable);
        } else {
            return customerRepository.findByNameContainingIgnoreCaseAndType(keyword, Customer.CustomerType.valueOf(type), pageable);
        }
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        for (Long id : ids) {
            customerRepository.deleteById(id);
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public long countCustomers() {
        return customerRepository.count();
    }
}
