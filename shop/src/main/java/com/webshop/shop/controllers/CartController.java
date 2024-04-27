package com.webshop.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.webshop.shop.service.CartService;

@Controller
public class CartController {

    private CartService cartService;
    
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    
    @GetMapping("/cart")
    public String getMethodName(Model model) {
        model.addAttribute("shopingCart", cartService.getShopingCart());  
        return "cart";
    }
}
