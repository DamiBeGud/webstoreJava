package com.webshop.shop.dto;

import lombok.Data;

@Data
public class UserDto {
    private int id;
    private String email;
    private String name;
    private String street;
    private int number;
    private int zip;
    private String Country;
    private String role;
    private String image;
}
