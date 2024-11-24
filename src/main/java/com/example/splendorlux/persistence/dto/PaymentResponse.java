package com.example.splendorlux.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private Long paymentId;
    private Long salesId;
    private String payerId;
    private String paymentStatus;
    private LocalDateTime paymentDate;
    private String paymentMethod;
    private Double paymentAmount;

}
