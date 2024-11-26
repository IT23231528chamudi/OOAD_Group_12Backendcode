package com.example.splendorlux.persistence.service;

import com.example.splendorlux.persistence.dto.CartItemResponse;
import com.example.splendorlux.persistence.entity.Cart;
import com.example.splendorlux.persistence.entity.CartItem;

import java.util.List;

public interface CartService {
    void addItemToCart(Long cartId, Long productId, Long quantity);

    List<CartItem> getCartItems(Long cartId);
    Cart getCartByUserId(Long userId);
    List<CartItemResponse> getCartItemsWithDetails(Long cartId);

    void clearCart(Long cartId);
}
