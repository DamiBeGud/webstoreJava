package com.webshop.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.webshop.shop.security.SecurityUtil;
import com.webshop.shop.service.CartService;
import com.webshop.shop.service.OrderService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OrdersController {
    private OrderService orderService;
    private CartService cartService;

    @Autowired
    public OrdersController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @GetMapping("/order/success/{orderId}")
    public String getOrderSuccessPage(@PathVariable int orderId, Model model) {
        model.addAttribute("order", orderService.getUserOrder(orderId));
        String email = SecurityUtil.getSessionUser();
        if (email != null) {
            model.addAttribute("productsInCart", cartService.getNumberOfProductsInCart());
        }
        return "orderSuccess";
    }

    @PostMapping("/cart/checkout/order")
    public String placeOrder() {

        int orderId = orderService.createUserOrder();
        return "redirect:/order/success/" + orderId;
    }

    @GetMapping("/orders")
    public String getOrdersPage(Model model) {
        model.addAttribute("orders", orderService.getUserOrders());
        String email = SecurityUtil.getSessionUser();
        if (email != null) {
            model.addAttribute("productsInCart", cartService.getNumberOfProductsInCart());
        }
        return "ordersUserPage";
    }

}
