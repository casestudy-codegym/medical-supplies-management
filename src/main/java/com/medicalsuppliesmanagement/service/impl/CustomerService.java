package com.medicalsuppliesmanagement.service.impl;

import com.medicalsuppliesmanagement.dto.CustomerDto;
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
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
}
