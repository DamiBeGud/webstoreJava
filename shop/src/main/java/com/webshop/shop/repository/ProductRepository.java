package com.webshop.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webshop.shop.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByUserId(int id);

    List<Product> findAllByNameContainingIgnoreCaseAndUserId(String name, Integer userId);

    List<Product> findAllByDiscountIsTrue();

    List<Product> findAllByCategory(int categoryId);

    List<Product> findAllBySubCategory(int subcategoryId);

    List<Product> findAllByNameContainingIgnoreCase(String searchString);
}
