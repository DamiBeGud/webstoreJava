package com.webshop.shop.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webshop.shop.dto.CsvFileUploadDto;
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
        product.setStock(productDto.getStock());
        product.setUserId(productDto.getUserId());
        product.setCategory(productDto.getCategory());
        product.setSubCategory(productDto.getSubCategory());
        product.setImage(productDto.getImage());

        Product newProduct = productRepository.save(product);

        ProductDto productResponse = new ProductDto();
        productResponse.setId(newProduct.getId());
        productResponse.setName(newProduct.getName());
        productResponse.setDescription(newProduct.getDescription());
        productResponse.setPrice(newProduct.getPrice());
        productResponse.setUserId(newProduct.getUserId());
        productResponse.setStock(newProduct.getStock());
        productResponse.setCategory(newProduct.getCategory());
        productResponse.setSubCategory(newProduct.getSubCategory());

        return productResponse;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> product = productRepository.findAll();
        return product.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
    }

    @Override
    public ProductDto getOneProductById(int id) {
        // Add exception error
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return mapToDto(product);
    }

    @Override
    public List<ProductDto> getAllProductsWithUserId(int id) {
        List<Product> products = productRepository.findAllByUserId(id);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found with the provided name");
        }
        return products.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllProductsWithNameWithUserId(String name, int id) {
        List<Product> products = productRepository.findAllByNameContainingIgnoreCaseAndUserId(name, id);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found with the provided name");
        }
        return products.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> createProductCsv(CsvFileUploadDto csvFileUploadDto) {
        String csvUrl = csvFileUploadDto.getCsvUrl();
        List<Product> productList = new ArrayList<>();
        try {
            URL url = new URL(csvUrl);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            boolean skipFirstLine = true;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                if (skipFirstLine) {
                    skipFirstLine = false;
                    continue; // Skip the first line
                }
                Product product = parseCSVLine(line, csvFileUploadDto.getUserId());
                System.out.println(product);
                productList.add(i, product);
                i++;
            }
            reader.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        List<Product> newProductsList = productRepository.saveAll(productList);
        return newProductsList.stream().map(npl -> mapToDto(npl)).collect(Collectors.toList());
    }

    private ProductDto mapToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setUserId(product.getUserId());
        productDto.setStock(product.getStock());
        productDto.setCategory(product.getCategory());
        productDto.setSubCategory(product.getSubCategory());
        return productDto;
    }

    private Product mapToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        return product;
    }

    private static Product parseCSVLine(String line, int userId) {
        // Define the regex pattern for parsing CSV lines
        String regex = "([^,]+),([^,]+),(\\d*\\.?\\d+),(\\d+),(.*?),(\\d+),(\\d+)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            // Extract fields from the CSV line
            String name = matcher.group(1);
            String description = matcher.group(2);
            double price = Double.parseDouble(matcher.group(3));
            int stock = Integer.parseInt(matcher.group(4));
            String image = matcher.group(5);
            int category = Integer.parseInt(matcher.group(6));
            int subCategory = Integer.parseInt(matcher.group(7));

            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStock(stock);
            product.setImage(image);
            product.setCategory(category);
            product.setSubCategory(subCategory);
            product.setUserId(userId);

            return product;
        } else {
            // Handle the case where the line does not match the expected format
            throw new IllegalArgumentException("Invalid CSV format: " + line);
        }
    }

}
