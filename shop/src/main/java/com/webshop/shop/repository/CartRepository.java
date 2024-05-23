package com.webshop.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.webshop.shop.models.Cart;

import jakarta.transaction.Transactional;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUserId(int userId);

    Cart findFirstByUserIdAndActive(int userId, Boolean active);

    @Modifying
    @Transactional
    void deleteAllByCartProductId(int productId);
}
