package com.webshop.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webshop.shop.models.ProductOrderModel;

public interface ProductOrderModelRepository extends JpaRepository<ProductOrderModel, Integer> {

}
