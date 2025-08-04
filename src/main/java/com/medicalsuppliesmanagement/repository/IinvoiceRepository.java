package com.medicalsuppliesmanagement.repository;

import com.medicalsuppliesmanagement.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IinvoiceRepository extends JpaRepository<Invoice, Long> {
}
