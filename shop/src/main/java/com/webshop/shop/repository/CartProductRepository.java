package com.webshop.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webshop.shop.models.CartProduct;

public interface CartProductRepository extends JpaRepository<CartProduct, Integer>{
    
}
