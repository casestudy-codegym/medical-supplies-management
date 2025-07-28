package com.medicalsuppliesmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "return_or_cancel_invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ReturnOrCancelInvoice {

    public enum ReturnType {
        RETURN,   // Trả hàng (refund)
        CANCEL    // Hủy hàng (destroy)
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "return_code", unique = true, nullable = false, length = 20)
    private String returnCode;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;  // Người thực hiện

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Customer supplier;  // Nhà cung cấp

    @Column(name = "return_date", nullable = false)
    private LocalDateTime returnDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "return_type", nullable = false, length = 10)
    private ReturnType returnType;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @OneToMany(mappedBy = "returnOrCancelInvoice", cascade = CascadeType.ALL)
    private List<ReturnOrCancelInvoiceDetail> details;
}

