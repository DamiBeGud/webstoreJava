package com.webshop.shop.dto;

import java.time.LocalDate;
import java.util.List;

import com.webshop.shop.models.Cart;
import com.webshop.shop.models.Product;

import lombok.Data;

@Data
public class OrderUserDto {
    private int id;
    private int userId;
    private LocalDate date;
    private Cart cart;
    private List<ProductDto> orderedProducts;
    private double total;

}
