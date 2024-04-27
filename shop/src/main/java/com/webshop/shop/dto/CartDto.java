package com.webshop.shop.dto;

import java.util.ArrayList;
import java.util.List;

import com.webshop.shop.models.CartProduct;

import lombok.Data;
@Data
public class CartDto {
    private int id;
    private int userId;
    private Boolean active = true;
    // private List<CartProduct> cart = new ArrayList<>();
    private List<CartProductDto> cart = new ArrayList<>();
    private double total;
    private int numberOfProducts;
}
