package com.webshop.shop.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String email;
    private String password;
    private String name;
    private String street;
    private String city;
    private int number;
    private int zip;
    private String Country;
}
