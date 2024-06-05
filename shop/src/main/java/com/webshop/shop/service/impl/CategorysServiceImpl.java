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

/**
 * Implements the categories service, managing business logic associated with
 * product categories and subcategories.
 */
@Service
public class CategorysServiceImpl implements CategorysService {
    private CategoryRepository categoryRepository;
    private SubCategoryRepository subCategoryRepository;

    /**
     * Constructs the service with necessary repository injections for managing
     * categories and subcategories.
     */
    @Autowired
    public CategorysServiceImpl(CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    /**
     * Retrieves all categories from the database and maps them to their DTO form.
     * 
     * @return a list of category DTOs.
     */
    @Override
    public List<CategoryDto> getAllCategorys() {
        List<Category> category = categoryRepository.findAll();
        return category.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
    }

    /**
     * Retrieves all subcategories associated with a specific category ID and maps
     * them to DTO form.
     * 
     * @param id the category ID for which subcategories are to be retrieved.
     * @return a list of subcategory DTOs corresponding to the category ID.
     */
    @Override
    public List<SubCategoryDto> getAllSubCategoriesWithCategoryId(int id) {
        List<SubCategory> subCategories = subCategoryRepository.findByCategoryId(id);
        System.out.println(subCategories);
        return subCategories.stream().map(sc -> mapToDtoSub(sc)).collect(Collectors.toList());
    }

    /**
     * Retrieves all subcategories from the database and maps them to their DTO
     * form.
     * 
     * @return a list of all subcategory DTOs.
     */
    @Override
    public List<SubCategoryDto> getAllSubCategorys() {
        List<SubCategory> subCategories = subCategoryRepository.findAll();

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
