package com.medicalsuppliesmanagement.service;

import com.medicalsuppliesmanagement.entity.Employee;
import com.medicalsuppliesmanagement.entity.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    Employee addEmployeeWithAccount(Employee employee, UserAccount userAccount);
    Employee save(Employee employee);
    Page<Employee> findAll(Pageable pageable);
    Optional<Employee> findById(Long id);
    Page<Employee> searchByFullName(String keyword, Pageable pageable);
    Page<Employee> searchEmployees(String keyword, String position, Pageable pageable);
    Optional<Employee> findByEmployeeCode(String employeeCode);
    List<String> getAllDistinctPositions();
    Employee updateEmployee(Employee employee);
    void deleteById(Long id);
    void deleteMultiple(List<Long> employeeIds);
    boolean existsByEmployeeCode(String employeeCode);
    long countEmployees();
}