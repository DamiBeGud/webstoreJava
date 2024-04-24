package com.webshop.shop.controllers.api.category;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webshop.shop.dto.SubCategoryDto;
import com.webshop.shop.models.SubCategory;
import com.webshop.shop.service.CategorysService;

@RestController
@RequestMapping("/api/v1/")
public class CategoryControllerREST {
    private CategorysService categorysService;

    public CategoryControllerREST(CategorysService categorysService) {
        this.categorysService = categorysService;
    }

    @GetMapping("subcategory/{id}")
    public ResponseEntity getSubCategory(@PathVariable int id) {
        return new ResponseEntity<>(categorysService.getAllSubCategoriesWithCategoryId(id), HttpStatus.OK);
    }
}
