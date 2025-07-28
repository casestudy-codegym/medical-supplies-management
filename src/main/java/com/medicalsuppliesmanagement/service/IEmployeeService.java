package com.medicalsuppliesmanagement.service;

import com.medicalsuppliesmanagement.entity.Employee;
import com.medicalsuppliesmanagement.entity.UserAccount;

public interface IEmployeeService {
    Employee addEmployeeWithAccount(Employee employee, UserAccount userAccount);
}
