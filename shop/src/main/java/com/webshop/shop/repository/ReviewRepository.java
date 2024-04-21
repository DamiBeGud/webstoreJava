package com.webshop.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webshop.shop.models.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findReviewByProductId(int id);
}
