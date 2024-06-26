package com.webshop.shop.service;

import com.webshop.shop.dto.RegisterDto;
import com.webshop.shop.dto.UserDto;
import com.webshop.shop.models.UserEntity;

public interface UserService {
    Integer saveUser(RegisterDto registerDto, String role);

    UserEntity findByEmail(String email);

    UserDto getUserById(int id);

    UserDto getUser();
}
