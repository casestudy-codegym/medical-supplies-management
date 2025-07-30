package com.medicalsuppliesmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "return_or_cancel_invoice_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ReturnOrCancelInvoiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "return_or_cancel_invoice_id", nullable = false)
    private ReturnOrCancelInvoice returnOrCancelInvoice;

    @ManyToOne
    @JoinColumn(name = "supply_id", nullable = false)
    private MedicalSupply medicalSupply;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price_returned")
    private Double priceReturned;
}

