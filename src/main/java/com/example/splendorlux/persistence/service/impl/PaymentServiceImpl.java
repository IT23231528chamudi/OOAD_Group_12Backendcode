package com.example.splendorlux.persistence.service.impl;

import com.example.splendorlux.persistence.dto.SalesRequest;
import com.example.splendorlux.persistence.dto.SalesResponse;
import com.example.splendorlux.persistence.entity.*;
import com.example.splendorlux.persistence.dto.PaymentRequest;
import com.example.splendorlux.persistence.dto.PaymentResponse;
import com.example.splendorlux.persistence.repository.CartRepository;
import com.example.splendorlux.persistence.repository.PaymentRepository;
import com.example.splendorlux.persistence.repository.ProductRepository;
import com.example.splendorlux.persistence.repository.SalesRepository;
import com.example.splendorlux.persistence.service.PaymentService;
import com.example.splendorlux.persistence.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final SalesRepository salesRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

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

    @Override
    public PaymentResponse processCartPayment(PaymentRequest paymentRequest) {
        // Validate cartId
        Long cartId = paymentRequest.getCartId();
        if (cartId == null) {
            throw new IllegalArgumentException("Cart ID must not be null");
        }

        // Fetch the cart
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Fetch all items in the cart
        List<CartItem> cartItems = cart.getCartItems();

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty or invalid cart ID");
        }

        // Initialize total payment amount
        double totalPaymentAmount = 0.0;

        Sales firstSales = null; // To keep track of the first sales record

        // Create sales records for each cart item
        for (CartItem cartItem : cartItems) {
            Sales sales = new Sales();
            sales.setProductId(cartItem.getProduct());
            sales.setQuantity(cartItem.getQuantity());
            sales.setSaleDate(new Date());
            sales.setUnitPrice(cartItem.getUnitPrice());
            sales.setTotalPrice(cartItem.getTotalPrice());
            sales.setStatus("pending");

            // Save sales record
            sales = salesRepository.save(sales);

            // Set the first sales record for the payment
            if (firstSales == null) {
                firstSales = sales;
            }

            // Update total payment amount
            totalPaymentAmount += cartItem.getTotalPrice();
        }

        // Create a payment record with the total amount
        Payment payment = new Payment();
        payment.setSales(firstSales); // Link to the first sales record
        payment.setPayerId(paymentRequest.getPayerId());
        payment.setPaymentStatus(paymentRequest.getPaymentStatus());
        payment.setPaymentDate(paymentRequest.getPaymentDate());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setPaymentAmount(totalPaymentAmount);

        // Save the payment record
        Payment savedPayment = paymentRepository.save(payment);

        // Delete processed cart items
        cartRepository.deleteById(cartId);

        // Map saved payment to response DTO
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPaymentId(savedPayment.getPaymentId());
        paymentResponse.setPayerId(savedPayment.getPayerId());
        paymentResponse.setPaymentStatus(savedPayment.getPaymentStatus());
        paymentResponse.setPaymentDate(savedPayment.getPaymentDate());
        paymentResponse.setPaymentMethod(savedPayment.getPaymentMethod());
        paymentResponse.setPaymentAmount(savedPayment.getPaymentAmount());

        return paymentResponse;
    }

}
