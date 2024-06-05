package com.webshop.shop.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webshop.shop.dto.CsvFileUploadDto;
import com.webshop.shop.dto.ProductDto;
import com.webshop.shop.dto.ReviewDto;
import com.webshop.shop.exceptions.ProductNotFoundException;
import com.webshop.shop.models.Product;
import com.webshop.shop.repository.ProductRepository;
import com.webshop.shop.service.CartService;
import com.webshop.shop.service.ProductService;
import com.webshop.shop.service.ReviewService;

/**
 * Implementation of ProductService that handles CRUD operations for products.
 */
@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ReviewService reviewService;

    /**
     * Constructs a ProductServiceImpl with required services.
     *
     * @param productRepository Repository for product data operations.
     * @param reviewService     Service for managing product reviews.
     */
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ReviewService reviewService) {
        this.productRepository = productRepository;
        this.reviewService = reviewService;

    }

    /**
     * Creates a new product based on the provided data transfer object.
     *
     * @param productDto Data transfer object containing product details.
     * @return A ProductDto containing the created product's details.
     */
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
        productResponse.setImage(newProduct.getImage());

        return productResponse;
    }

    /**
     * Retrieves a list of all products.
     *
     * @return A list of ProductDto each representing a product.
     */
    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> product = productRepository.findAll();
        return product.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
    }

    /**
     * Retrieves a list of all products with computed ratings for display in the
     * shop.
     *
     * @return A list of ProductDto each with an added rating field.
     */
    @Override
    public List<ProductDto> getAllProductsShop() {
        List<Product> product = productRepository.findAll();
        List<ProductDto> productDtos = product.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        List<ProductDto> response = productDtos.stream().map(products -> {
            products.setRating(rating(products.getId()));
            return products;
        }).collect(Collectors.toList());
        return response;
    }

    /**
     * Retrieves a single product by its unique identifier.
     *
     * @param id The unique identifier of the product.
     * @return A ProductDto representing the product.
     * @throws ProductNotFoundException If no product is found with the provided id.
     */
    @Override
    public ProductDto getOneProductById(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        ProductDto productDto = mapToDto(product);

        productDto.setRating(rating(productDto.getId()));
        return productDto;
    }

    private Integer rating(int id) {
        int rating;
        List<ReviewDto> reviews = reviewService.findReviewsByProductId(id);
        if (reviews.size() == 0) {
            rating = 0;
            return rating;
        } else {
            int reviewTotal = 0;
            for (ReviewDto review : reviews) {
                reviewTotal = reviewTotal + review.getRating();
            }
            rating = reviewTotal / reviews.size();
            return rating;
        }
    }

    /**
     * Retrieves all products owned by a specific user.
     *
     * @param id The user's unique identifier.
     * @return A list of ProductDto.
     */

    @Override
    public List<ProductDto> getAllProductsWithUserId(int id) {
        List<Product> products = productRepository.findAllByUserId(id);
        return products.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
    }

    /**
     * Retrieves all products that contain the provided name and are owned by a
     * specific user.
     *
     * @param name The partial or full name of the product to find.
     * @param id   The user's unique identifier.
     * @return A list of ProductDto.
     */

    @Override
    public List<ProductDto> getAllProductsWithNameWithUserId(String name, int id) {
        List<Product> products = productRepository.findAllByNameContainingIgnoreCaseAndUserId(name, id);
        return products.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
    }

    /**
     * Creates multiple products from a CSV file uploaded via URL.
     *
     * @param csvFileUploadDto DTO containing the URL of the CSV file and the user's
     *                         id.
     * @return A list of ProductDto representing the newly created products.
     */
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

    /**
     * Updates the stock quantity of an existing product.
     *
     * @param id    The unique identifier of the product.
     * @param stock The number to add to the current stock.
     * @return A ProductDto with the updated stock.
     * @throws ProductNotFoundException If no product is found with the provided id.
     */
    @Override
    public ProductDto updateStock(int id, int stock) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + "was not found"));
        product.setStock(product.getStock() + stock);
        Product updateProduct = productRepository.save(product);
        return mapToDto(updateProduct);
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
        productDto.setDiscount(product.getDiscount());
        productDto.setDiscountPrice(product.getDiscountPrice());
        productDto.setImage(product.getImage());
        productDto.setDiscontinued(product.getDiscontinued());
        return productDto;
    }

    private Product mapToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        return product;
    }

    /**
     * Parses a single line of a CSV to create a Product entity.
     *
     * @param line   The CSV line to parse.
     * @param userId The user's unique identifier to associate with the product.
     * @return A Product entity populated with the CSV line data.
     */
    private static Product parseCSVLine(String line, int userId) {
        // Define the regex pattern for parsing CSV lines
        String regex = "^(.+?),(.*?),(\\d*\\.?\\d+),(\\d+),(.*?),(1|2|3),(\\d+),(FALSE|TRUE),(\\d+)$";

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
            boolean discount = Boolean.parseBoolean(matcher.group(8));
            double discountPrice = Double.parseDouble(matcher.group(9));

            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStock(stock);
            product.setImage(image);
            product.setCategory(category);
            product.setSubCategory(subCategory);
            product.setDiscount(discount);
            product.setDiscountPrice(discountPrice);
            product.setUserId(userId);

            return product;
        } else {
            // Handle the case where the line does not match the expected format
            throw new IllegalArgumentException("Invalid CSV format: " + line);
        }
    }

    /**
     * Updates the details of an existing product based on the provided DTO.
     * If the new price is lower than the old price, a discount is applied.
     *
     * @param productDto The product details to update.
     * @return A ProductDto representing the updated product.
     * @throws ProductNotFoundException If the product cannot be found.
     */
    @Override
    public ProductDto updateProduct(ProductDto productDto) {

        Product product = productRepository.findById(productDto.getId()).orElseThrow(
                () -> new ProductNotFoundException("Product with id " + productDto.getId() + "was not found"));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());

        double newPrice = productDto.getPrice();
        double oldPrice = product.getPrice();

        if (newPrice < oldPrice) {
            product.setDiscountPrice(newPrice);
            product.setDiscount(true);
        } else {
            product.setPrice(productDto.getPrice());
            product.setDiscount(false);
        }

        Product updateProduct = productRepository.save(product);

        return mapToDto(updateProduct);
    }

    /**
     * Retrieves a product by its ID specifically for order services.
     *
     * @param id The ID of the product to retrieve.
     * @return The found product.
     * @throws ProductNotFoundException If no product is found with the provided ID.
     */

    @Override
    public Product getOneProductByIdForOrderService(int id) {

        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product was not found"));
    }

    /**
     * Decreases the stock of a product by a specified amount.
     *
     * @param id    The ID of the product to update.
     * @param stock The amount to decrease from the product's stock.
     * @throws ProductNotFoundException If the product with the given ID is not
     *                                  found.
     */
    @Override
    public void updateStockOrder(int id, int stock) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + "was not found"));
        product.setStock(product.getStock() - stock);
        productRepository.save(product);

    }

    /**
     * Retrieves all products currently marked with a special deal (discount).
     *
     * @return A list of ProductDto for products with discounts.
     */
    @Override
    public List<ProductDto> getSpecialDealProducts() {
        List<Product> products = productRepository.findAllByDiscountIsTrue();
        List<ProductDto> productDtos = products.stream().map(prod -> {
            return mapToDto(prod);
        }).collect(Collectors.toList());
        return productDtos;
    }

    /**
     * Retrieves all products that belong to a specific category.
     *
     * @param id The category ID.
     * @return A list of ProductDto corresponding to the products in the given
     *         category.
     */

    @Override
    public List<ProductDto> getProductsByCategory(int id) {
        List<Product> products = productRepository.findAllByCategory(id);
        List<ProductDto> productDtos = products.stream().map(prod -> mapToDto(prod)).collect(Collectors.toList());
        return productDtos;

    }

    /**
     * Retrieves all products that belong to a specific subcategory.
     *
     * @param id The subcategory ID.
     * @return A list of ProductDto corresponding to the products in the given
     *         subcategory.
     */

    @Override
    public List<ProductDto> getProductsBySubCategory(int id) {
        List<Product> products = productRepository.findAllBySubCategory(id);
        List<ProductDto> productDtos = products.stream().map(prod -> mapToDto(prod)).collect(Collectors.toList());
        return productDtos;
    }

    /**
     * Searches for products by name, ignoring case.
     *
     * @param searchString The string to search for in product names.
     * @return A list of ProductDto for products whose names contain the search
     *         string.
     */

    @Override
    public List<ProductDto> searchForProducts(String searcString) {
        List<Product> products = productRepository.findAllByNameContainingIgnoreCase(searcString);
        List<ProductDto> productDtos = products.stream().map(prod -> mapToDto(prod)).collect(Collectors.toList());
        return productDtos;
    }

    /**
     * Sorts a list of products based on a specified sorting criterion.
     *
     * @param sortBy   The sorting criterion ("priceAscending", "priceDescending",
     *                 "nameAZ", "nameZA").
     * @param products The list of products to sort.
     * @return The sorted list of ProductDto.
     */
    @Override
    public List<ProductDto> sortProducts(String sortBy, List<ProductDto> products) {
        switch (sortBy) {
            case "priceAscending":
                sortByPriceAscending(products);
                break;
            case "priceDescending":
                sortByPriceDescending(products);
                break;
            case "nameAZ":
                sortByNameAZ(products);
                break;
            case "nameZA":
                sortByNameZA(products);
                break;

        }
        return products;
    }

    private static List<ProductDto> sortByPriceAscending(List<ProductDto> products) {
        Collections.sort(products, Comparator.comparing(ProductDto::getPrice));
        return products;
    }

    private static List<ProductDto> sortByPriceDescending(List<ProductDto> products) {
        Collections.sort(products, Comparator.comparing(ProductDto::getPrice).reversed());
        return products;
    }

    private static List<ProductDto> sortByNameAZ(List<ProductDto> products) {
        Collections.sort(products, Comparator.comparing(ProductDto::getName));
        return products;
    }

    private static List<ProductDto> sortByNameZA(List<ProductDto> products) {
        Collections.sort(products, Comparator.comparing(ProductDto::getName).reversed());
        return products;
    }

    /**
     * Filters products within a specified price range.
     *
     * @param from     The lower bound of the price range.
     * @param to       The upper bound of the price range.
     * @param products The list of products to filter.
     * @return A list of ProductDto that fall within the specified price range.
     */

    @Override
    public List<ProductDto> filterProducts(double from, double to, List<ProductDto> products) {

        List<ProductDto> filteredProducts = products.stream()
                .filter(product -> product.getPrice() >= from && product.getPrice() <= to).collect(Collectors.toList());
        return filteredProducts;
    }

    /**
     * Discontinues a product by marking it as discontinued in the database.
     *
     * @param productId The ID of the product to discontinue.
     * @return A ProductDto representing the discontinued product.
     * @throws ProductNotFoundException If the product is not found.
     */
    @Override
    public ProductDto discontinueProduct(int productId) {

        Product discontinueProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not Found"));
        discontinueProduct.setDiscontinued(true);
        productRepository.save(discontinueProduct);

        return mapToDto(discontinueProduct);
    }
}
