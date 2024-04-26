package com.webshop.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webshop.shop.models.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer>{
    
}
