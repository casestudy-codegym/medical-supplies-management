package com.medicalsuppliesmanagement.service.impl;

import com.medicalsuppliesmanagement.entity.Invoice;
import com.medicalsuppliesmanagement.entity.InvoiceItem;
import com.medicalsuppliesmanagement.entity.Material;
import com.medicalsuppliesmanagement.repository.IinvoiceRepository;
import com.medicalsuppliesmanagement.repository.IMaterialRepository; // <-- Thêm repo này
import com.medicalsuppliesmanagement.service.IinvoiceService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvoiceService implements IinvoiceService {

    @Autowired
    private IinvoiceRepository iinvoiceRepository;

    @Autowired
    private IMaterialRepository materialRepository; // <-- Thêm

    @Override
    public Invoice getInvoiceById(Long id) {
        return iinvoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));
    }

    @Override
    public Invoice updateInvoice(Long id, Invoice invoice) {
        Invoice existingInvoice = getInvoiceById(id);
        existingInvoice.setCustomer(invoice.getCustomer());
        existingInvoice.setEmployee(invoice.getEmployee());
        existingInvoice.setCreatedAt(invoice.getCreatedAt());
        existingInvoice.setTotalAmount(invoice.getTotalAmount());
        return iinvoiceRepository.save(existingInvoice);
    }

    @Override
    public void deleteInvoice(Long id) {
        if (!iinvoiceRepository.existsById(id)) {
            throw new RuntimeException("Invoice not found with id: " + id);
        }
        iinvoiceRepository.deleteById(id);
    }

    @Transactional
    public Invoice createInvoiceForMaterial(Long materialId, Integer quantity) {
        // Lấy vật tư
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material not found"));

        // Tạo hóa đơn
        Invoice invoice = new Invoice();
        invoice.setInvoiceCode(UUID.randomUUID().toString());
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setTotalAmount(material.getPrice() * quantity);

        // Tạo item hóa đơn
        InvoiceItem item = new InvoiceItem();
        item.setMaterial(material.toMedicalSupply());
        item.setQuantity(quantity);
        item.setPrice(material.getPrice());
        item.setInvoice(invoice);

        // Gắn list items
        invoice.setInvoiceItems(Collections.singletonList(item));

        // Save cascade (Invoice -> InvoiceItem)
        return iinvoiceRepository.save(invoice);
    }

    @Override
    public Optional<Invoice> findById(Long id) {
       return iinvoiceRepository.findById(id).or(() -> {;
           throw new RuntimeException("Không tìm thấy hóa đơn với ID: " + id);
       });
    }

    @Transactional
    public void confirmPayment(Long invoiceId, String paymentMethod) {
        Invoice invoice = iinvoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));
        invoice.setNote("Thanh toán bằng " +
                ("CASH".equals(paymentMethod) ? "Tiền mặt" : "Chuyển khoản"));
        iinvoiceRepository.save(invoice);
    }

}
