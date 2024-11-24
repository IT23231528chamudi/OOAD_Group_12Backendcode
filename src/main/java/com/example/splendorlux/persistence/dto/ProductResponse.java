package com.example.splendorlux.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProductResponse {
    private Long productID;
    private String  productName;
    private String productSize;
    private Long categoryID;
    private Long stockQuantity;
    private Long price;
    private String productImage;
}
