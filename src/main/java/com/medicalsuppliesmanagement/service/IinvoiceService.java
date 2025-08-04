package com.medicalsuppliesmanagement.service;

import com.medicalsuppliesmanagement.entity.Invoice;

import java.util.Optional;

public interface IinvoiceService {

    Invoice getInvoiceById(Long id);

    Invoice updateInvoice(Long id, Invoice invoice);

    void deleteInvoice(Long id);

    Invoice createInvoiceForMaterial(Long id, Integer quantity);
    Optional<Invoice> findById(Long id);
}
