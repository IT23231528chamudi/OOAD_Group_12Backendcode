package com.example.splendorlux.persistence.service.impl;

import com.example.splendorlux.persistence.dto.SalesRequest;
import com.example.splendorlux.persistence.dto.SalesResponse;
import com.example.splendorlux.persistence.entity.Payment;
import com.example.splendorlux.persistence.dto.PaymentRequest;
import com.example.splendorlux.persistence.dto.PaymentResponse;
import com.example.splendorlux.persistence.entity.Products;
import com.example.splendorlux.persistence.entity.Sales;
import com.example.splendorlux.persistence.repository.PaymentRepository;
import com.example.splendorlux.persistence.repository.ProductRepository;
import com.example.splendorlux.persistence.repository.SalesRepository;
import com.example.splendorlux.persistence.service.PaymentService;
import com.example.splendorlux.persistence.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final SalesRepository salesRepository;
    private final ProductRepository productRepository;

    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        Sales sales;

        // Check if salesRequest is provided to create a new Sales record
        if (paymentRequest.getSalesRequest() != null) {
            SalesRequest salesRequest = paymentRequest.getSalesRequest();
            sales = new Sales();

            // Check if productId is valid in the SalesRequest
            if (salesRequest.getProductId() == null) {
                throw new IllegalArgumentException("Product ID must not be null in the sales request");
            }

            // Find product by ID
            Products product = productRepository.findById(salesRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product Not Found"));

            // Set sales properties
            sales.setProductId(product);
            sales.setQuantity(salesRequest.getQuantity());
            sales.setSaleDate(salesRequest.getSaleDate());
            sales.setUnitPrice(salesRequest.getUnitPrice());
            sales.setTotalPrice(salesRequest.getTotalPrice());
            sales.setStatus("pending");

            // Save the sales record
            sales = salesRepository.save(sales);
        } else if (paymentRequest.getSalesId() != null) {
            // If salesId is provided, fetch the existing Sales record
            sales = salesRepository.findById(paymentRequest.getSalesId())
                    .orElseThrow(() -> new RuntimeException("Sale not found"));
        } else {
            throw new IllegalArgumentException("Either salesId or salesRequest must be provided");
        }

        // Create and save the Payment record
        Payment payment = new Payment();
        payment.setSales(sales);
        payment.setPayerId(paymentRequest.getPayerId());
        payment.setPaymentStatus(paymentRequest.getPaymentStatus());
        payment.setPaymentDate(paymentRequest.getPaymentDate());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setPaymentAmount(paymentRequest.getPaymentAmount());

        Payment savedPayment = paymentRepository.save(payment);

        // Map saved payment to response DTO
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPaymentId(savedPayment.getPaymentId());
        paymentResponse.setSalesId(savedPayment.getSales().getSalesId());
        paymentResponse.setPayerId(savedPayment.getPayerId());
        paymentResponse.setPaymentStatus(savedPayment.getPaymentStatus());
        paymentResponse.setPaymentDate(savedPayment.getPaymentDate());
        paymentResponse.setPaymentMethod(savedPayment.getPaymentMethod());
        paymentResponse.setPaymentAmount(savedPayment.getPaymentAmount());

        return paymentResponse;
    }
}
