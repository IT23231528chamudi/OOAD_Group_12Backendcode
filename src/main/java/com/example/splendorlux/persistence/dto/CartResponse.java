package com.example.splendorlux.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private Long cartItemId;
    private Long productId;
    private String productName;
    private Long productPrice;
    private Long quantity;
    private Long unitPrice;
    private Long totalPrice;
}
