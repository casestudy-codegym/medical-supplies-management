package com.medicalsuppliesmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "medical_supplies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalSupply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supply_id")
    private Long supplyId;

    @Column(name = "supply_code", unique = true, length = 20)
    private String supplyCode;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "price")
    private Double price;

    @Column(name = "unit", length = 20)
    private String unit;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Customer supplier;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public void setId(Long id) {
        this.supplyId = id;
    }
}