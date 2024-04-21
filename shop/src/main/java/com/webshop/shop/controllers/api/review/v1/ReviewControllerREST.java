package com.webshop.shop.controllers.api.review.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.webshop.shop.dto.ReviewDto;
import com.webshop.shop.service.ReviewService;

@RestController
@RequestMapping("/api/v1/review/")
public class ReviewControllerREST {
    private ReviewService reviewService;

    @Autowired
    public ReviewControllerREST(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto) {
        return new ResponseEntity<>(reviewService.createReview(reviewDto), HttpStatus.CREATED);
    }

}
