package com.webshop.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.webshop.shop.security.SecurityUtil;
import com.webshop.shop.service.CategorysService;
import com.webshop.shop.service.OrderService;
import com.webshop.shop.service.ProductService;
import com.webshop.shop.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardController {
    private UserService userService;
    private ProductService productService;
    private CategorysService categorysService;
    private OrderService orderService;

    @Autowired
    public DashboardController(UserService userService, ProductService productService,
            CategorysService categorysService, OrderService orderService) {
        this.userService = userService;
        this.productService = productService;
        this.categorysService = categorysService;
        this.orderService = orderService;
    }

    @GetMapping("/dashboard/{id}")
    public String getDashboardPage(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        String email = SecurityUtil.getSessionUser();
        if (email != null) {
            model.addAttribute("user", userService.getUser());
        }
        return "shopDashboard";
    }

    @GetMapping("/dashboard/{id}/products")
    public String getDashboardsProductsPage(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("products", productService.getAllProductsWithUserId(id));
        model.addAttribute("categoryList", categorysService.getAllCategorys());
        model.addAttribute("subCategoryList", categorysService.getAllSubCategorys());
        String email = SecurityUtil.getSessionUser();
        if (email != null) {
            model.addAttribute("user", userService.getUser());
        }
        return "products";
    }

    @GetMapping("/dashboard/{id}/product/add")
    public String getDashboardsAddProductPage(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("categories", categorysService.getAllCategorys());
        String email = SecurityUtil.getSessionUser();
        if (email != null) {
            model.addAttribute("user", userService.getUser());
        }
        return "addProduct";
    }

    @GetMapping("/dashboard/{id}/orders")
    public String getOrdersPageDashboard(@PathVariable int id, Model model) {
        String email = SecurityUtil.getSessionUser();
        if (email != null) {
            model.addAttribute("user", userService.getUser());
        }
        model.addAttribute("orders", orderService.getCompanyOrders());
        return "orders";
    }

    @GetMapping("/dashboard/{id}/order/{orderId}")
    public String getSingleOrderDahsboardPage(@PathVariable int id, @PathVariable int orderId, Model model) {
        String email = SecurityUtil.getSessionUser();
        if (email != null) {
            model.addAttribute("user", userService.getUser());
        }
        model.addAttribute("order", orderService.getCompanyOrder(orderId));
        return "singleDashboardOrder";
    }

}
