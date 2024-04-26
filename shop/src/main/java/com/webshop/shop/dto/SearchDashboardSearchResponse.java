package com.webshop.shop.dto;

import java.util.List;

import lombok.Data;
@Data
public class SearchDashboardSearchResponse {
    private List<ProductDto> products;
    private List<CategoryDto> categories;
    private List<SubCategoryDto> subCategories;
}
