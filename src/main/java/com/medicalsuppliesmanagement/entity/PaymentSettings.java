package com.medicalsuppliesmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payment_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSettings {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "account_name")
    private String accountName;
    
    @Column(name = "account_number")
    private String accountNumber;
    
    @Column(name = "bank_code")
    private String bankCode;
    
    @Column(name = "bank_name")
    private String bankName;
    
    @Column(name = "is_active")
    private Boolean isActive;
    
    @Column(name = "last_update", columnDefinition = "TIMESTAMP")
    private String lastUpdate;
    
    @Column(name = "default_content_template", columnDefinition = "TEXT")
    private String defaultContentTemplate;
    
    @Column(name = "bank_short_name")
    private String bankShortName;
}
