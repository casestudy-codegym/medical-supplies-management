package com.medicalsuppliesmanagement.repository;

import com.medicalsuppliesmanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUser_Username(String username);
}
