package com.webshop.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String getOrderSuccessPage(@PathVariable int orderId, Model model) {
        model.addAttribute("order", orderService.getUserOrder(orderId));
        System.out.println("order  :" + orderService.getUserOrder(orderId));
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
        return "ordersUserPage";
    }

}
