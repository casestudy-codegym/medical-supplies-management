package com.medicalsuppliesmanagement.service;

import com.medicalsuppliesmanagement.dto.EmployeeDto;

public interface IEmployeeService {
    EmployeeDto getEmployeeProfile(String username);
    void updateEmployee(EmployeeDto dto);
}
