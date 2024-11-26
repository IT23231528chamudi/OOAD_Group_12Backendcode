package com.example.splendorlux.persistence.repository;

import com.example.splendorlux.persistence.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByCartId(Long cartId);
    void deleteByCartId(Long cartId);
    Cart findByUserId(Long userId);
}
