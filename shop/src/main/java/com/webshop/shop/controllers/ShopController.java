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
import com.webshop.shop.models.UserEntity;
import com.webshop.shop.repository.UserRepository;
import com.webshop.shop.security.SecurityUtil;
import com.webshop.shop.service.ProductService;
import com.webshop.shop.service.ReviewService;
import com.webshop.shop.service.UserService;

@Controller
public class ShopController {

    private ProductService productService;
    private ReviewService reviewService;
    private UserService userService;


    @Autowired
    public ShopController(ProductService productService, ReviewService reviewService, UserService userService) {
        this.productService = productService;
        this.reviewService = reviewService;
        this.userService = userService;

    }

    @GetMapping("/shop")
    public ModelAndView getProducts() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", productService.getAllProductsShop());
        String email = SecurityUtil.getSessionUser();
        if(email != null){
            modelAndView.addObject("user", userService.getUser());
        }
        modelAndView.setViewName("allProducts");

        return modelAndView;
    }

    @GetMapping("/product/{id}")
    public ModelAndView getProduct(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("product", productService.getOneProductById(id));
        modelAndView.addObject("reviews", reviewService.findReviewsByProductId(id));
        String email = SecurityUtil.getSessionUser();
        if(email != null){
            modelAndView.addObject("user", userService.getUser());
        }
        modelAndView.setViewName("product");
        return modelAndView;
    }

    @GetMapping("/cart")
    public String getMethodName() {

        return "cart";
    }
    

}
