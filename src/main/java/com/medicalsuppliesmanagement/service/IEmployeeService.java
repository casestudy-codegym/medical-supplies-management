package com.medicalsuppliesmanagement.service;

import com.medicalsuppliesmanagement.entity.Employee;
import com.medicalsuppliesmanagement.entity.UserAccount;
import com.medicalsuppliesmanagement.dto.EmployeeDto;

public interface IEmployeeService {
    EmployeeDto getEmployeeProfile(String username);
    void updateEmployee(EmployeeDto dto);
    Employee addEmployeeWithAccount(Employee employee, UserAccount userAccount);
}