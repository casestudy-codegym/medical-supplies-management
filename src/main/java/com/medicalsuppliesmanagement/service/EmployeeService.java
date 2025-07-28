package com.medicalsuppliesmanagement.service;


import com.medicalsuppliesmanagement.entity.Employee;
import com.medicalsuppliesmanagement.repository.IEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private IEmployeeRepository repository;

    @Override
    public List<Employee> findAll() {
        return repository.findAll();
    }
}
