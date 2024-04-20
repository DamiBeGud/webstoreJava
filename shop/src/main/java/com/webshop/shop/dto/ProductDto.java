package com.webshop.shop.dto;

import lombok.Data;

@Data
public class ProductDto {
    private int id;
    private String name;
    private String description;
    private int price;
}
