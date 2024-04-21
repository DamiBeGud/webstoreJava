package com.webshop.shop.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private int id;
    private int productId;
    private String title;
    private String review;
    private int rating;
}
