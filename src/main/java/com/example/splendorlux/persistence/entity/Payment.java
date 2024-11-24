package com.example.splendorlux.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "sales_id", nullable = false)
    private Sales sales;

    private String payerId;
    private String paymentStatus;
    private LocalDateTime paymentDate = LocalDateTime.now();
    private String paymentMethod;
    private Double paymentAmount;
}
