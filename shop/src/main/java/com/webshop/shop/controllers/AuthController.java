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
import com.webshop.shop.service.LoggerService;
import com.webshop.shop.service.UserService;

@Controller
public class AuthController {
    private UserService userService;
    private LoggerService loggerService;

    @Autowired
    public AuthController(UserService userService, LoggerService loggerService) {
        this.userService = userService;
        this.loggerService = loggerService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/register/user")
    public String getRegisterUserPage(Model model) {
        RegisterDto user = new RegisterDto();
        model.addAttribute("user", user);
        return "registerUser";
    }

    @GetMapping("/register/company")
    public String getRegisterCompanyPage(Model model) {
        RegisterDto user = new RegisterDto();
        model.addAttribute("user", user);
        return "registerCompany";
    }

    @PostMapping("/register/company/save")
    public String registerCompany(@ModelAttribute("user") RegisterDto user, Model model, BindingResult result) {
        loggerService.info("Register Company was called");
        loggerService.debug("Debugging Register Company");

        UserEntity existingUserEmail = userService.findByEmail(user.getEmail());
        if (existingUserEmail != null && existingUserEmail.getEmail() != null
                && !existingUserEmail.getEmail().isEmpty()) {
            result.rejectValue("email", "duplicate.email", "There is already a user with this email");
            loggerService.error("There is already a user with this email");

        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            loggerService.error("Error occured");

            return "registerCompany";
        }
        int userId = userService.saveUser(user, "ROLE_ADMIN");

        return "redirect:/dashboard/" + userId;
    }

    @PostMapping("/register/user/save")
    public String registerUser(@ModelAttribute("user") RegisterDto user, Model model, BindingResult result) {
        loggerService.info("Register User was called");
        loggerService.debug("Debugging Register User");
        UserEntity existingUserEmail = userService.findByEmail(user.getEmail());
        if (existingUserEmail != null && existingUserEmail.getEmail() != null
                && !existingUserEmail.getEmail().isEmpty()) {
            result.rejectValue("email", "duplicate.email", "There is already a user with this email");
            loggerService.error("There is already a user with this email");

        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            loggerService.error("Error occured");
            return "registerCompany";
        }
        int userId = userService.saveUser(user, "ROLE_USER");
        return "redirect:/dashboard/" + userId;
    }
}
