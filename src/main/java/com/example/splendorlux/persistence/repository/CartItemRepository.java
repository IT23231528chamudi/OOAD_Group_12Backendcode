package com.example.splendorlux.persistence.repository;

import com.example.splendorlux.persistence.entity.Cart;
import com.example.splendorlux.persistence.entity.CartItem;
import com.example.splendorlux.persistence.entity.Products;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart_CartId(Long cartId);
    CartItem findByCartAndProduct(Cart cart, Products product);
    @Transactional
    void deleteByCart_CartId(Long cartId);

}
