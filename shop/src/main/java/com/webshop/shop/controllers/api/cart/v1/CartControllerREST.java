package com.webshop.shop.controllers.api.cart.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webshop.shop.service.CartService;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/")
public class CartControllerREST {
    private CartService cartService;
    
    @Autowired
    public CartControllerREST(CartService cartService) {
        this.cartService = cartService;
    }


    @PostMapping("user/{userId}/cart/{cartId}/add/{productId}")
    public ResponseEntity addProductToCart(
        @PathVariable int userId,
        @PathVariable int cartId,
        @PathVariable int productId){
        System.out.println("Controller   " + userId + cartId + productId);
        cartService.addProductToCart(userId, cartId, productId);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("/cart/{cartId}/remove/{productId}")
    public ResponseEntity postMethodName(@PathVariable int cartId, @PathVariable int productId) {
       int response = cartService.removeProductFromCart(cartId, productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
}
