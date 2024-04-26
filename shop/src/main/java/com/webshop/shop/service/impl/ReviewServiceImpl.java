package com.webshop.shop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

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
        review.setName(reviewDto.getName());

        Review newReview = reviewRepository.save(review);

        ReviewDto reviewResponse = new ReviewDto();
        reviewResponse.setId(newReview.getId());
        reviewResponse.setProductId(newReview.getProductId());
        reviewResponse.setTitle(newReview.getTitle());
        reviewResponse.setReview(newReview.getReview());
        reviewResponse.setRating(newReview.getRating());
        reviewResponse.setName(newReview.getName());


        return reviewResponse;

    }

    @Override
    public List<ReviewDto> findReviewsByProductId(int id) {
        List<Review> reviews = reviewRepository.findReviewsByProductId(id);

        return reviews.stream().map(r -> mapToDto(r)).collect(Collectors.toList());
    }

    private ReviewDto mapToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        // reviewDto.setId(review.getId());
        // reviewDto.setProductId(0);
        reviewDto.setName(review.getName());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setReview(review.getReview());
        reviewDto.setRating(review.getRating());
        return reviewDto;
    }
}
