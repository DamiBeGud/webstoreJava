package com.webshop.shop.dto;

import java.time.LocalDate;
import java.util.List;

import org.apache.catalina.User;

import com.webshop.shop.models.Product;

import lombok.Data;

@Data
public class OrderCompanyDto {
    private int id;
    private int userId;
    private int companyId;
    private LocalDate date;
    private LocalDate dateShipped;
    private Boolean status;
    private List<Product> orderedProducts;
    private UserDto customer;
}
