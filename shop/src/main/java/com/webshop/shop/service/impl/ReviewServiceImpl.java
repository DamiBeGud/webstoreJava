package com.webshop.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.webshop.shop.dto.ReviewDto;
import com.webshop.shop.models.Review;
import com.webshop.shop.repository.ReviewRepository;
import com.webshop.shop.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ReviewDto createReview(ReviewDto reviewDto) {
        Review review = new Review();
        review.setProductId(reviewDto.getProductId());
        review.setTitle(reviewDto.getTitle());
        review.setReview(reviewDto.getReview());
        review.setRating(reviewDto.getRating());

        Review newReview = reviewRepository.save(review);

        ReviewDto reviewResponse = new ReviewDto();
        reviewResponse.setId(newReview.getId());
        reviewResponse.setProductId(0);
        reviewResponse.setTitle(newReview.getTitle());
        reviewResponse.setReview(newReview.getReview());
        reviewResponse.setRating(newReview.getRating());

        return reviewResponse;

    }

}
