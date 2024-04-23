package com.webshop.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webshop.shop.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByUserId(int id);
}
