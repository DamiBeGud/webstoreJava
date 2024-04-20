package com.webstore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthPagesController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
