package com.webshop.shop.service;

import java.util.List;

import com.webshop.shop.dto.CsvFileUploadDto;
import com.webshop.shop.dto.ProductDto;
import com.webshop.shop.models.Product;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);

    List<ProductDto> getAllProducts();

    List<ProductDto> getAllProductsShop();

    List<ProductDto> getAllProductsWithUserId(int id);

    ProductDto getOneProductById(int id);

    ProductDto updateStock(int id, int stock);

    void updateStockOrder(int id, int stock);

    ProductDto updateProduct(ProductDto productDto);

    List<ProductDto> getAllProductsWithNameWithUserId(String name, int id);

    List<ProductDto> createProductCsv(CsvFileUploadDto csvFileUploadDto);

    Product getOneProductByIdForOrderService(int id);

    List<ProductDto> getSpecialDealProducts();

    List<ProductDto> getProductsByCategory(int id);

    List<ProductDto> getProductsBySubCategory(int id);

    List<ProductDto> searchForProducts(String searcString);

    List<ProductDto> sortProducts(String sortBy, List<ProductDto> products);

    List<ProductDto> filterProducts(double from, double to, List<ProductDto> products);

    ProductDto discontinueProduct(int productId);

}
