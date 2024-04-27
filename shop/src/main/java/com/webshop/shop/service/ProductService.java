package com.webshop.shop.service;

import java.util.List;

import com.webshop.shop.dto.CsvFileUploadDto;
import com.webshop.shop.dto.ProductDto;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);

    List<ProductDto> getAllProducts();
    
    List<ProductDto> getAllProductsShop();

    List<ProductDto> getAllProductsWithUserId(int id);

    ProductDto getOneProductById(int id);

    ProductDto updateStock(int id, int stock);

    ProductDto updateProduct(ProductDto productDto);

    List<ProductDto> getAllProductsWithNameWithUserId(String name, int id);

    List<ProductDto> createProductCsv(CsvFileUploadDto csvFileUploadDto);
}
