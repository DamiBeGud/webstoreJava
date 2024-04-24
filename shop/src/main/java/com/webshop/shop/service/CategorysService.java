package com.webshop.shop.service;

import java.util.List;

import com.webshop.shop.dto.CategoryDto;
import com.webshop.shop.dto.SubCategoryDto;

public interface CategorysService {

    List<CategoryDto> getAllCategorys();

    List<SubCategoryDto> getAllSubCategoriesWithCategoryId(int id);
}
