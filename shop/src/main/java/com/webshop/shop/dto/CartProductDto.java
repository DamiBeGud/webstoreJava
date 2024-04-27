package com.webshop.shop.dto;

import lombok.Data;

@Data
public class CartProductDto {
    private int id;
    private int productId;
    private String name;
    private Double price;
    private int companyId;
    private String image;
    private int qty;
}
