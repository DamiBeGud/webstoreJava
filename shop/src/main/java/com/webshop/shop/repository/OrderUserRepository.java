package com.webshop.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webshop.shop.models.OrderUser;

public interface OrderUserRepository extends JpaRepository <OrderUser, Integer>{
    
}
