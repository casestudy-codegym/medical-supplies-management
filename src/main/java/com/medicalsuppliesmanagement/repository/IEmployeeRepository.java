package com.medicalsuppliesmanagement.repository;

import com.medicalsuppliesmanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmployeeCode(String employeeCode);
    Optional<Employee> findByUser_Username(String username);
}
