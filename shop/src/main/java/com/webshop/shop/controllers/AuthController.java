package com.webshop.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.webshop.shop.dto.RegisterDto;
import com.webshop.shop.models.UserEntity;
import com.webshop.shop.service.UserService;

@Controller
public class AuthController {
    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "shop/login";
    }

    @GetMapping("/register/user")
    public String getRegisterUserPage() {
        return "shop/registerUser";
    }

    @GetMapping("/register/company")
    public String getRegisterCompanyPage(Model model) {
        RegisterDto user = new RegisterDto();
        model.addAttribute("user", user);
        return "shop/registerCompany";
    }

    @PostMapping("/register/company/save")
    public String registerCompany(@ModelAttribute("user") RegisterDto user, Model model, BindingResult result) {

        UserEntity existingUserEmail = userService.findByEmail(user.getEmail());
        if (existingUserEmail != null && existingUserEmail.getEmail() != null
                && !existingUserEmail.getEmail().isEmpty()) {
            result.rejectValue("email", "duplicate.email", "There is already a user with this email");

        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "shop/registerCompany";
        }
        userService.saveUser(user);
        return "redirect:/dashboard";
    }
}