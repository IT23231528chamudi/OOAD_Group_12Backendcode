package com.example.splendorlux.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private Long cartItemId;
    private Long cartId;
    private Long userId;
    private Long productId;
    private String productName;
    private String productSize;
    private String productImage;
    private Long quantity;
    private Long unitPrice;
    private Long totalPrice;
}
