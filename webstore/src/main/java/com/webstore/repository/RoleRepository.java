package com.webstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webstore.models.Roles;

public interface RoleRepository extends JpaRepository<Roles, Integer> {
    Optional<Roles> findByName(String name);
}
