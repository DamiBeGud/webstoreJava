package com.webshop.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webshop.shop.models.OrderCompany;

public interface OrderCompanyRepository extends JpaRepository<OrderCompany, Integer> {
    List<OrderCompany> findAllByCompanyId(int userId);
}
