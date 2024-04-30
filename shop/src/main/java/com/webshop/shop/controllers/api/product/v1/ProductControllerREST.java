package com.webshop.shop.controllers.api.product.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.webshop.shop.dto.CsvFileUploadDto;
import com.webshop.shop.dto.ProductDto;
import com.webshop.shop.dto.request.SearchRequestDto;
import com.webshop.shop.dto.response.SearchDashboardSearchResponse;
import com.webshop.shop.models.Product;
import com.webshop.shop.service.CategorysService;
import com.webshop.shop.service.ProductService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/product/")
public class ProductControllerREST {

    private ProductService productService;
    private CategorysService categorysService;

    @Autowired
    public ProductControllerREST(ProductService productService, CategorysService categorysService) {
        this.productService = productService;
        this.categorysService = categorysService;
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

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable int id) {
        return new ResponseEntity<>(productService.getOneProductById(id), HttpStatus.OK);
    }

    @PostMapping("company/search")
    public ResponseEntity<SearchDashboardSearchResponse> getProductsCompanySearch(
            @RequestBody SearchRequestDto searchRequestDto) {
        System.out.println(searchRequestDto);
        SearchDashboardSearchResponse searchDashboardSearchResponse = new SearchDashboardSearchResponse();
        searchDashboardSearchResponse
                .setProducts(productService.getAllProductsWithNameWithUserId(searchRequestDto.getSearchString(),
                        searchRequestDto.getUserId()));
        searchDashboardSearchResponse.setCategories(categorysService.getAllCategorys());
        searchDashboardSearchResponse.setSubCategories(categorysService.getAllSubCategorys());

        // return null;
        return new ResponseEntity<>(searchDashboardSearchResponse, HttpStatus.OK);
    }

    @PostMapping("create/csv")
    public ResponseEntity<List<ProductDto>> createProductCsv(@RequestBody CsvFileUploadDto csvFileUploadDto) {
        System.err.println(csvFileUploadDto.getCsvUrl());
        System.err.println(csvFileUploadDto.getUserId());

        return new ResponseEntity<>(productService.createProductCsv(csvFileUploadDto), HttpStatus.CREATED);
    }

    @PostMapping("update/stock")
    public ResponseEntity<ProductDto> updateStock(@RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productService.updateStock(productDto.getId(), productDto.getStock()),
                HttpStatus.OK);
    }

    @PostMapping("update")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productService.updateProduct(productDto),
                HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<List<ProductDto>> getSearchProduct(@RequestParam("search") String searchString) {
        List<ProductDto> searchResults = productService.searchForProducts(searchString);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }

}
