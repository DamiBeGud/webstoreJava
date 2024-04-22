package com.webshop.shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DashboardController {

    @GetMapping("/dashboard/{id}")
    public String getDashboardPage(@PathVariable int id) {

        return "shopDashboard";
    }
}
