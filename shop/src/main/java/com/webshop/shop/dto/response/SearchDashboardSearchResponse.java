package com.webshop.shop.dto.response;

import java.util.List;

import com.webshop.shop.dto.CategoryDto;
import com.webshop.shop.dto.ProductDto;
import com.webshop.shop.dto.SubCategoryDto;

import lombok.Data;
@Data
public class SearchDashboardSearchResponse {
    private List<ProductDto> products;
    private List<CategoryDto> categories;
    private List<SubCategoryDto> subCategories;
}
