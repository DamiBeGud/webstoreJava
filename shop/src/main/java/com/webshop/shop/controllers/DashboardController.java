package com.webshop.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.webshop.shop.service.CategorysService;
import com.webshop.shop.service.ProductService;
import com.webshop.shop.service.UserService;

@Controller
public class DashboardController {
    private UserService userService;
    private ProductService productService;
    private CategorysService categorysService;

    @Autowired
    public DashboardController(UserService userService, ProductService productService,
            CategorysService categorysService) {
        this.userService = userService;
        this.productService = productService;
        this.categorysService = categorysService;
    }

    @GetMapping("/dashboard/{id}")
    public String getDashboardPage(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "shopDashboard";
    }

    @GetMapping("/dashboard/{id}/products")
    public String getDashboardsProductsPage(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("products", productService.getAllProductsWithUserId(id));
        model.addAttribute("categoryList", categorysService.getAllCategorys());
        model.addAttribute("subCategoryList", categorysService.getAllSubCategorys());
        return "products";
    }

    @GetMapping("/dashboard/{id}/product/add")
    public String getDashboardsAddProductPage(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("categories", categorysService.getAllCategorys());
        return "addProduct";
    }

}
