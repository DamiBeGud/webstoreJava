package com.webshop.shop.controllers.api.cart.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webshop.shop.service.CartService;
import com.webshop.shop.service.LoggerService;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/")
public class CartControllerREST {
    private CartService cartService;
    private LoggerService loggerService;

    @Autowired
    public CartControllerREST(CartService cartService, LoggerService loggerService) {
        this.cartService = cartService;
        this.loggerService = loggerService;
    }

    @PostMapping("user/{userId}/cart/{cartId}/add/{productId}")
    public ResponseEntity addProductToCart(
            @PathVariable int userId,
            @PathVariable int cartId,
            @PathVariable int productId) {
        loggerService.info("Cart Add Product To Cart Endpoint Called");
        loggerService.debug("Debugging Add Product To Cart");

        try {
            cartService.addProductToCart(userId, cartId, productId);
        } catch (Exception e) {
            loggerService.error("An error has occurred", e);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("cart/{cartId}/remove/{productId}")
    public ResponseEntity removeProductFromCart(@PathVariable int cartId, @PathVariable int productId) {
        loggerService.info("Cart Remove Product To Cart Endpoint Called");
        loggerService.debug("Debugging Remove Product To Cart");
        int response = cartService.removeProductFromCart(cartId, productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("cart/update/qty/{cartProductId}/value/{qty}")
    public ResponseEntity updateQty(@PathVariable int cartProductId, @PathVariable int qty) {
        loggerService.info("Cart Update Qty Endpoint Called");
        loggerService.debug("Debugging Update Qty");
        return new ResponseEntity<>(cartService.updateQty(cartProductId, qty), HttpStatus.OK);
    }

}
