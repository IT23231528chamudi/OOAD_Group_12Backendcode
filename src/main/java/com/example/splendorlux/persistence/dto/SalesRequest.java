package com.example.splendorlux.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SalesRequest {
    private Long productId;
    private Long quantity;
    private Date saleDate;
    private Long unitPrice;
    private Long totalPrice;
    private String status;
}
