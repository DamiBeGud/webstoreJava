package com.webshop.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.webshop.shop.dto.RegisterDto;
import com.webshop.shop.models.UserEntity;
import com.webshop.shop.repository.UserRepository;
import com.webshop.shop.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Integer saveUser(RegisterDto registerDto) {
        UserEntity user = new UserEntity();
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole("ADMIN");
        user.setName(registerDto.getName());
        user.setNumber(registerDto.getNumber());
        user.setStreet(registerDto.getStreet());
        user.setZip(registerDto.getZip());
        user.setCountry(registerDto.getCountry());
        userRepository.save(user);

        return user.getId();

    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
