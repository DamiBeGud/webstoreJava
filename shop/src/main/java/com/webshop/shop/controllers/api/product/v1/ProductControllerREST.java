package com.webshop.shop.controllers.api.product.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.webshop.shop.dto.ProductDto;
import com.webshop.shop.models.Product;
import com.webshop.shop.service.ProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/product/")
public class ProductControllerREST {

    private ProductService productService;

    @Autowired
    public ProductControllerREST(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {

        return new ResponseEntity<>(productService.createProduct(productDto), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<ProductDto>> getProducts() {

        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

}
