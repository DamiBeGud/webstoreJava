package com.webshop.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webshop.shop.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
