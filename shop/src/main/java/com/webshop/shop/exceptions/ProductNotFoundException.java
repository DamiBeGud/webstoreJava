package com.webshop.shop.exceptions;

public class ProductNotFoundException extends RuntimeException {
    private static final long serilalVersionID = 1;

    public ProductNotFoundException(String message) {
        super(message);

    }
}
