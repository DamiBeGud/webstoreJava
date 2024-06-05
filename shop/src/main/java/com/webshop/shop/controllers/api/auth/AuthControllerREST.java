package com.webshop.shop.controllers.api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webshop.shop.dto.LoginDto;
import com.webshop.shop.dto.RegisterDto;
import com.webshop.shop.models.UserEntity;
import com.webshop.shop.repository.UserRepository;
import com.webshop.shop.service.LoggerService;

@RestController
@RequestMapping("/api/auth/")
public class AuthControllerREST {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private LoggerService loggerService;

    @Autowired
    public AuthControllerREST(AuthenticationManager authenticationManager, UserRepository userRepository,
            PasswordEncoder passwordEncoder, LoggerService loggerService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.loggerService = loggerService;
    }

    @PostMapping("register/{role}")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto, @PathVariable String role) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Account with that email already exists", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = new UserEntity();
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        if (role.equals("admin")) {
            user.setRole("ADMIN");
        } else if (role.equals("user")) {
            user.setRole("USER");

        }

        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        loggerService.info("Login Endpoint Called");
        loggerService.debug("Debugging Login");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            loggerService.error("An error occurred in login endpoint", e);

        }
        return new ResponseEntity<>("User loged in successfuly", HttpStatus.OK);
    }

}
