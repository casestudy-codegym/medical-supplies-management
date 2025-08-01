package com.medicalsuppliesmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    public enum CustomerType {
        WHOLESALE, SUPPLIER, RETAIL // (sỉ, nhà cc, lẻ )
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(name = "customer_code", unique = true, length = 20)
    private String customerCode;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CustomerType type;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    public String getTypeVN() {
        return switch (this.type) {
            case WHOLESALE -> "Khách sỉ";
            case SUPPLIER -> "Nhà cung cấp";
            case RETAIL -> "Khách lẻ";
        };
    }

}

