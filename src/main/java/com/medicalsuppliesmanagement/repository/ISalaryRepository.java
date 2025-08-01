package com.medicalsuppliesmanagement.repository;

import com.medicalsuppliesmanagement.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISalaryRepository extends JpaRepository<Salary, Long> {
    Optional<Salary> findFirstByEmployee_User_UsernameOrderByYearDescMonthDesc(String username);
}
