package com.medicalsuppliesmanagement.service;


import com.medicalsuppliesmanagement.entity.Employee;

import java.util.List;

public interface IEmployeeService {
    List<Employee> findAll();
}
