package com.medicalsuppliesmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "salaries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salaryId;

    private int month; // tháng lương
    private int year; // năm lương

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "base_salary")
    private Double baseSalary;

    @Column(name = "advance")
    private Double advance; // tạm ứng lương

    @Column(name = "salary_due")
    private Double salaryDue; // = base_salary - advance

    private LocalDate payment_date; // ngày trả lương

    private PaymentStatus payment_status;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum PaymentStatus {
        PENDING, PAID, CANCELLED
    }
}

