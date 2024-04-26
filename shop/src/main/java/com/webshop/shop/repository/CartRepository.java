package com.webshop.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webshop.shop.models.Cart;
import java.util.List;


public interface CartRepository extends JpaRepository<Cart, Integer>{
    List<Cart> findByUserId(int userId);
}
