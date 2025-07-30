package com.medicalsuppliesmanagement.repository;

import com.medicalsuppliesmanagement.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findByUserAccountFullNameContainingIgnoreCase(String keyword, Pageable pageable);
    boolean existsByEmployeeCode(String employeeCode);
}
