package com.medicalsuppliesmanagement.service.impl;


import com.medicalsuppliesmanagement.dto.CustomerDto;
import com.medicalsuppliesmanagement.entity.Customer;
import com.medicalsuppliesmanagement.repository.ICustomerRepository;
import com.medicalsuppliesmanagement.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final ICustomerRepository customerRepository;

    @Override
    public CustomerDto getCustomerProfile(String username) {
        Customer customer = customerRepository.findByUser_Username(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        CustomerDto dto = new CustomerDto();
        dto.setUsername(customer.getUser().getUsername());
        dto.setFullName(customer.getUser().getFullName());
        dto.setAvatarUrl(customer.getUser().getAvatarUrl());

        dto.setCustomerCode(customer.getCustomerCode());
        dto.setName(customer.getName());
        dto.setPhone(customer.getPhone());
        dto.setEmail(customer.getEmail());
        dto.setAddress(customer.getAddress());
        dto.setType(customer.getType());

        return dto;
    }
}
