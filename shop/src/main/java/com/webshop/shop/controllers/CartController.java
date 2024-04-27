package com.webshop.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.webshop.shop.service.CartService;
import com.webshop.shop.service.UserService;

@Controller
public class CartController {

    private CartService cartService;
    private UserService userService;
    
    @Autowired
    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    
    @GetMapping("/cart")
    public String getCartPage(Model model) {
        model.addAttribute("shopingCart", cartService.getShopingCart());  
        model.addAttribute("productsInCart", cartService.getNumberOfProductsInCart());
        return "cart";
    }
    @GetMapping("/cart/checkout")
    public String getCheckoutPage(Model model) {
        model.addAttribute("user", userService.getUser());
        System.out.println("Cart Checkout" + cartService.getShopingCart());
        model.addAttribute("shopingCart", cartService.getShopingCart()); 
        return "checkout";
    }

}
