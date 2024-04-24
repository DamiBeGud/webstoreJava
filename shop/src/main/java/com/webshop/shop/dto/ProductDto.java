package com.webshop.shop.dto;

import lombok.Data;

@Data
public class ProductDto {
    private int id;
    private String name;
    private String description;
    private Double price;
    private int userId;
    private int stock;
    private String image;
    private int category;
    private int subCategory;
}
