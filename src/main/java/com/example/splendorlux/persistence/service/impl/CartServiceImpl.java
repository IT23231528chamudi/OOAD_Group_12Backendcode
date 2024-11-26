package com.example.splendorlux.persistence.service.impl;

import com.example.splendorlux.persistence.dto.CartItemResponse;
import com.example.splendorlux.persistence.entity.Cart;
import com.example.splendorlux.persistence.entity.CartItem;
import com.example.splendorlux.persistence.entity.Products;
import com.example.splendorlux.persistence.repository.CartItemRepository;
import com.example.splendorlux.persistence.repository.CartRepository;
import com.example.splendorlux.persistence.repository.ProductRepository;
import com.example.splendorlux.persistence.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void addItemToCart(Long userId, Long productId, Long quantity) {
        System.out.println("Data: " + quantity);
        // Check if a cart already exists for the user
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            // If no cart exists, create a new one
            cart = new Cart();
            cart.setUserId(userId);
            cart = cartRepository.save(cart); // Save and retrieve the cart with ID
        }

        // Retrieve the product
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if the product already exists in the cart
        CartItem existingCartItem = cartItemRepository.findByCartAndProduct(cart, product);
        if (existingCartItem != null) {
            // Update the quantity and total price if the product is already in the cart
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            existingCartItem.setTotalPrice(existingCartItem.getUnitPrice() * existingCartItem.getQuantity());
            cartItemRepository.save(existingCartItem);
        } else {
            // Create a new CartItem
            Long unitPrice = product.getPrice();
            Long totalPrice = unitPrice * quantity;

            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity != null ? quantity : 1); // Ensure quantity is set to at least 1
            cartItem.setUnitPrice(unitPrice);
            cartItem.setTotalPrice(totalPrice);

            // Save the new cart item
            cartItemRepository.save(cartItem);
        }
    }


    @Override
    public List<CartItem> getCartItems(Long cartId) {
        return cartItemRepository.findByCart_CartId(cartId);
    }

    @Override
    @Transactional
    public void clearCart(Long cartId) {
        cartItemRepository.deleteByCart_CartId(cartId);
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public List<CartItemResponse> getCartItemsWithDetails(Long cartId) {
        // Fetch cart items for the given cart ID
        List<CartItem> cartItems = cartItemRepository.findByCart_CartId(cartId);

        // Map `CartItem` entities to `CartItemResponse` DTOs
        return cartItems.stream().map(cartItem -> {
            Products product = cartItem.getProduct();
            return new CartItemResponse(
                    cartItem.getCartItemId(),
                    cartItem.getCart().getCartId(),
                    cartItem.getCart().getUserId(),
                    product.getProductID(),
                    product.getProductName(),
                    product.getProductSize(),
                    product.getProductImage(),
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice(),
                    cartItem.getTotalPrice()
            );
        }).toList();
    }


}

