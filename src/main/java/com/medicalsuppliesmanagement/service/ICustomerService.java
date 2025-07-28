package com.medicalsuppliesmanagement.service;

import com.medicalsuppliesmanagement.dto.CustomerDto;

public interface ICustomerService {
    CustomerDto getCustomerProfile(String username);
}
