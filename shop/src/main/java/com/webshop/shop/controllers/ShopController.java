package com.webshop.shop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.webshop.shop.dto.ProductDto;
import com.webshop.shop.service.ProductService;

@Controller
public class ShopController {

    private ProductService productService;

    @Autowired
    public ShopController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/shop")
    public ModelAndView getProducts() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", productService.getAllProducts());
        modelAndView.setViewName("allProducts");

        return modelAndView;
    }

    @GetMapping("/product/{id}")
    public ModelAndView getProduct(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("product", productService.getOneProductById(id));
        modelAndView.setViewName("product");
        return modelAndView;
    }

}
