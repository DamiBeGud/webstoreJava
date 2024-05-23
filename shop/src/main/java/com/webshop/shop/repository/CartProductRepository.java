package com.webshop.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.webshop.shop.models.CartProduct;

public interface CartProductRepository extends JpaRepository<CartProduct, Integer> {

    List<CartProduct> findAllByProductId(int productId);
}
