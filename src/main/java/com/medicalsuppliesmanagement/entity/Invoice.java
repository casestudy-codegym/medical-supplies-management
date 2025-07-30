// hoa don ban hang
package com.medicalsuppliesmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "invoice_code")
    private String invoiceCode;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    @Column(name = "total_amount")
    private Double totalAmount;
    
    @Column(name = "discount_amount")
    private Double discountAmount;
    
    @Column(name = "discount_rate")
    private Integer discountRate;
    
    @Column(name = "discount_percent")
    private Double discountPercent;
    
    @Column(name = "vat_amount")
    private Double vatAmount;
    
    @Column(name = "vat_percent")
    private Double vatPercent;
    
    @Column(name = "note", columnDefinition = "TEXT")
    private String note;
    
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<InvoiceItem> invoiceItems;
}
