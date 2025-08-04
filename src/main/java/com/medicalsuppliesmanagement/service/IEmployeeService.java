package com.medicalsuppliesmanagement.service;

import com.medicalsuppliesmanagement.entity.Employee;
import com.medicalsuppliesmanagement.entity.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    Employee addEmployeeWithAccount(Employee employee, UserAccount userAccount);
    Optional<Employee> findById(Long id);
    Page<Employee> searchEmployees(String keyword, String position, Pageable pageable);
    List<String> getAllDistinctPositions();
    Employee updateEmployee(Employee employee);
    void deleteById(Long id);
    void deleteMultiple(List<Long> employeeIds);
    long countEmployees();
}