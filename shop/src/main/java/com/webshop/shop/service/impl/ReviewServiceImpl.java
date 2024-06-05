package com.webshop.shop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webshop.shop.dto.ReviewDto;
import com.webshop.shop.models.Review;
import com.webshop.shop.repository.ReviewRepository;
import com.webshop.shop.service.ReviewService;

/**
 * Service implementation for managing product reviews.
 * This service provides functionality to create and retrieve product reviews.
 */
@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;

    /**
     * Constructs a new ReviewServiceImpl with necessary dependency.
     *
     * @param reviewRepository the repository handling review data
     */
    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * Creates a new review based on the provided DTO and persists it.
     *
     * @param reviewDto DTO containing the review data to be persisted
     * @return the newly created ReviewDto with persisted data
     */
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

    /**
     * Retrieves all reviews for a specific product ID.
     *
     * @param id the product ID for which reviews are being requested
     * @return a list of ReviewDto representing all reviews for the specified
     *         product
     */
    @Override
    public List<ReviewDto> findReviewsByProductId(int id) {
        List<Review> reviews = reviewRepository.findReviewsByProductId(id);

        return reviews.stream().map(r -> mapToDto(r)).collect(Collectors.toList());
    }

    /**
     * Helper method to convert a Review entity to a ReviewDto.
     *
     * @param review the Review entity to convert
     * @return a ReviewDto containing the data from the Review entity
     */
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
