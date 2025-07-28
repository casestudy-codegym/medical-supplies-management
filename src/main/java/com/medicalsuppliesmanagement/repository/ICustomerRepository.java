package com.medicalsuppliesmanagement.repository;


import com.medicalsuppliesmanagement.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    Page<Customer> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    Optional<Customer> findByCustomerCode(String code);
}