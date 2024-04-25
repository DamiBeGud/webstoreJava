package com.webshop.shop.dto;

import lombok.Data;

@Data
public class CsvFileUploadDto {
    private int userId;
    private String csvUrl;
}
