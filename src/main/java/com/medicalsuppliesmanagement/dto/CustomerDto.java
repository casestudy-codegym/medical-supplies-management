package com.medicalsuppliesmanagement.dto;

import com.medicalsuppliesmanagement.entity.Customer.CustomerType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto {
    private String username;
    private String name;
    private String avatarUrl;
    private String customerCode;
    private String phone;
    private String email;
    private String address;
    private CustomerType type;
}
