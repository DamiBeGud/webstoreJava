package com.webshop.shop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webshop.shop.dto.CategoryDto;

import com.webshop.shop.dto.SubCategoryDto;
import com.webshop.shop.models.Category;
import com.webshop.shop.models.SubCategory;
import com.webshop.shop.repository.CategoryRepository;
import com.webshop.shop.repository.SubCategoryRepository;
import com.webshop.shop.service.CategorysService;

@Service
public class CategorysServiceImpl implements CategorysService {
    private CategoryRepository categoryRepository;
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    public CategorysServiceImpl(CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public List<CategoryDto> getAllCategorys() {
        List<Category> category = categoryRepository.findAll();
        return category.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
    }

    @Override
    public List<SubCategoryDto> getAllSubCategoriesWithCategoryId(int id) {
        List<SubCategory> subCategories = subCategoryRepository.findByCategoryId(id);
        System.out.println(subCategories);
        return subCategories.stream().map(sc -> mapToDtoSub(sc)).collect(Collectors.toList());
    }

    private SubCategoryDto mapToDtoSub(SubCategory subCategory) {
        SubCategoryDto subCategoryDto = new SubCategoryDto();
        subCategoryDto.setId(subCategory.getId());
        subCategoryDto.setName(subCategory.getName());
        subCategoryDto.setCategoryId(subCategory.getCategoryId());
        return subCategoryDto;
    }

    private CategoryDto mapToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }
}
