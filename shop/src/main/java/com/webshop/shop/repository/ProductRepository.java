package com.webshop.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webshop.shop.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
