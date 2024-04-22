package com.webshop.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webshop.shop.models.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);

    UserEntity findFirstByEmail(String email);

    Boolean existsByEmail(String email);
}
