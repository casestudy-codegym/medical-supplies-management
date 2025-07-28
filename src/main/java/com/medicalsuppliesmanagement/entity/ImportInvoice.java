// Hóa đơn nhập kho
package com.medicalsuppliesmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "import_invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportInvoice { // hóa đơn nhập kho
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "invoice_code")
    private String invoiceCode;
    
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Customer supplier;

    @Column(name = "total_amount")
    private Double totalAmount;
    
    @OneToMany(mappedBy = "importInvoice", cascade = CascadeType.ALL)
    private List<ImportInvoiceDetail> importInvoiceItems;
}
