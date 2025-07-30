package com.medicalsuppliesmanagement.service;

import com.medicalsuppliesmanagement.entity.Employee;
import com.medicalsuppliesmanagement.entity.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IEmployeeService {
    Employee addEmployeeWithAccount(Employee employee, UserAccount userAccount);
    Employee updateEmployee(Employee employee);
    Page<Employee> findAll(Pageable pageable);
    Page<Employee> searchByFullName(String keyword, Pageable pageable);
    Optional<Employee> findById(Long id);
    Employee save(Employee employee);
    void deleteById(Long id);
}