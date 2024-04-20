package com.webshop.shop.service;

import java.util.List;

import com.webshop.shop.dto.ProductDto;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);

    List<ProductDto> getAllProducts();
}
