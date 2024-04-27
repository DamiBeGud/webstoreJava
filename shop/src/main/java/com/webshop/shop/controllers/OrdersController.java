package com.webshop.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.webshop.shop.service.OrderService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class OrdersController {
    private OrderService orderService;

    @Autowired
    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/order/success/{orderId}")
    public String getOrderSuccessPage() {
        return "orderSuccess";
    }


    @PostMapping("/cart/checkout/order")
    public String placeOrder() {
        
        int orderId = orderService.createUserOrder();
        return "redirect:/order/success/" + orderId;
    }
    
    
}
