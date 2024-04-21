package com.webshop.shop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webshop.shop.dto.ProductDto;
import com.webshop.shop.exceptions.ProductNotFoundException;
import com.webshop.shop.models.Product;
import com.webshop.shop.repository.ProductRepository;
import com.webshop.shop.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());

        Product newProduct = productRepository.save(product);

        ProductDto productResponse = new ProductDto();
        productResponse.setId(newProduct.getId());
        productResponse.setName(newProduct.getName());
        productResponse.setDescription(newProduct.getDescription());
        productResponse.setPrice(newProduct.getPrice());
        return productResponse;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> product = productRepository.findAll();
        return product.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
    }

    private ProductDto mapToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        return productDto;
    }

    private Product mapToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        return product;
    }

    @Override
    public ProductDto getOneProductById(int id) {
        // Add exception error
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return mapToDto(product);
    }
}
