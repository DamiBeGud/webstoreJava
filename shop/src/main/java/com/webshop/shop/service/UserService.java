package com.webshop.shop.service;

import com.webshop.shop.dto.RegisterDto;
import com.webshop.shop.models.UserEntity;

public interface UserService {
    void saveUser(RegisterDto registerDto);

    UserEntity findByEmail(String email);
}
