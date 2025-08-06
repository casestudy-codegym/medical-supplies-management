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
    @Column(name = "salary_id")
    private Long salaryId;

    @Column(name = "month", nullable = false)
    private int month; // Tháng lương

    @Column(name = "year", nullable = false)
    private int year; // Năm lương

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "base_salary")
    private Double baseSalary; // Lương cơ bản

    @Column(name = "advance")
    private Double advance; // Tạm ứng lương

    @Column(name = "salary_due")
    private Double salaryDue; // Lương thực nhận = base_salary - advance

    @Column(name = "payment_date")
    private LocalDate paymentDate; // Ngày trả lương

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum PaymentStatus {
        PENDING, PAID, CANCELLED
    }
}
