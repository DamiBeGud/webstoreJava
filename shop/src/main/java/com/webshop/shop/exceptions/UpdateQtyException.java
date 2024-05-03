package com.webshop.shop.exceptions;

public class UpdateQtyException extends RuntimeException {
    private static final long serilalVersionID = 1;

    public UpdateQtyException(String message) {
        super(message);
    }
}
