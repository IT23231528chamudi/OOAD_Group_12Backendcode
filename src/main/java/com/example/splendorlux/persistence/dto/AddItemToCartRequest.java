package com.example.splendorlux.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddItemToCartRequest {
    private Long userId;
    private Long productId;
    private Long quantity;
}
