package com.webshop.shop.dto;

import java.time.LocalDate;

import com.webshop.shop.models.Cart;

import lombok.Data;

@Data
public class OrderUserDto {
    private int id;
    private int userId;
    private LocalDate date;
    private Cart cart;
    private double total;

}
