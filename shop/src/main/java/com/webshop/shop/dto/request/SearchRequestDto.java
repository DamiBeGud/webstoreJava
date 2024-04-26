package com.webshop.shop.dto.request;

import lombok.Data;

@Data
public class SearchRequestDto {
    private String searchString;
    private int userId;
}
