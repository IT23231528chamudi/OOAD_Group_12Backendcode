package com.example.splendorlux.persistence.service;

import com.example.splendorlux.persistence.dto.PaymentRequest;
import com.example.splendorlux.persistence.dto.PaymentResponse;
import com.example.splendorlux.persistence.dto.SalesRequest;

public interface PaymentService {
    PaymentResponse processPayment(PaymentRequest paymentRequest);
    PaymentResponse processCartPayment(PaymentRequest paymentRequest);
}