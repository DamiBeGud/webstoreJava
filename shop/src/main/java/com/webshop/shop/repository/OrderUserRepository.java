package com.webshop.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webshop.shop.models.OrderUser;

public interface OrderUserRepository extends JpaRepository<OrderUser, Integer> {
    List<OrderUser> findAllByUserId(int userId);
}
