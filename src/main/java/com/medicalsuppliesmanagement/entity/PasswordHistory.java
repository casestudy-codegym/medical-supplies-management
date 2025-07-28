package com.medicalsuppliesmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "account_id")
    private UserAccount account;
    
    @Column(name = "old_password")
    private String oldPassword;
    
    @Column(name = "changed_at")
    private LocalDateTime changedAt;
}
