package com.medicalsuppliesmanagement.repository;

import com.medicalsuppliesmanagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUser_Username(String username);
}
