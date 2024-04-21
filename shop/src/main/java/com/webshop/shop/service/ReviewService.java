package com.webshop.shop.service;

import java.util.List;

import com.webshop.shop.dto.ReviewDto;

public interface ReviewService {
    ReviewDto createReview(ReviewDto reviewDto);

    List<ReviewDto> findReviewsByProductId(int id);
}
