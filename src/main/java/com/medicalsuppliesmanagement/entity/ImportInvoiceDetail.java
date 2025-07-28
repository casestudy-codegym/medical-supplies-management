package com.medicalsuppliesmanagement.entity;

// Chi tiết hóa đơn nhập kho
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "import_invoice_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportInvoiceDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "import_invoice_id", nullable = false)
    private ImportInvoice importInvoice;

    @ManyToOne
    @JoinColumn(name = "supply_id", nullable = false)
    private MedicalSupply medicalSupply;
    
    @Column(name = "quantity")
    private Integer quantity;
    
    @Column(name = "unit_price")
    private Double unitPrice;
    
    @Column(name = "total_price")
    private Double totalPrice;
}
