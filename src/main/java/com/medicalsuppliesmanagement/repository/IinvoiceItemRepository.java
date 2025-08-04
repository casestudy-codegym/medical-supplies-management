package com.medicalsuppliesmanagement.repository;

import com.medicalsuppliesmanagement.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IinvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
    // Additional query methods can be defined here if needed
}
