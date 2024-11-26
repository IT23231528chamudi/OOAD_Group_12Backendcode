package com.example.splendorlux.controller;

import com.example.splendorlux.persistence.dto.AddItemToCartRequest;
import com.example.splendorlux.persistence.dto.CartItemResponse;
import com.example.splendorlux.persistence.entity.Cart;
import com.example.splendorlux.persistence.entity.CartItem;
import com.example.splendorlux.persistence.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/add-item")
    public ResponseEntity<String> addItemToCart(@RequestBody AddItemToCartRequest request) {
        cartService.addItemToCart(request.getUserId(), request.getProductId(), request.getQuantity());
        return ResponseEntity.ok("Item added to cart");
    }

    @GetMapping("/{cartId}/items")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable Long cartId) {
        List<CartItem> cartItems = cartService.getCartItems(cartId);
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<String> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.ok("Cart cleared");
    }

    @GetMapping("/user/{userId}/items")
    public ResponseEntity<List<CartItemResponse>> getCartItemsByUserId(@PathVariable Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null) {
            return ResponseEntity.ok(List.of()); // Return an empty list if no cart exists for the user
        }
        List<CartItemResponse> cartItems = cartService.getCartItemsWithDetails(cart.getCartId());
        return ResponseEntity.ok(cartItems);
    }

}
