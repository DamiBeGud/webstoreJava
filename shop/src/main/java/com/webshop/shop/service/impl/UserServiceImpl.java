package com.webshop.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.webshop.shop.dto.RegisterDto;
import com.webshop.shop.dto.UserDto;
import com.webshop.shop.models.UserEntity;
import com.webshop.shop.repository.UserRepository;
import com.webshop.shop.security.SecurityUtil;
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
    public Integer saveUser(RegisterDto registerDto, String role) {
        UserEntity user = new UserEntity();
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(role);
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

    @Override
    public UserDto getUserById(int id) {
        UserEntity userEntity = userRepository.getById(id);

        return mapToDtoUser(userEntity);
    }

    @Override
    public UserDto getUser() {

        String email = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByEmail(email);
        System.out.println(user);
        return mapToDtoUser(user);
    }

    private UserDto mapToDtoUser(UserEntity userEntity){
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setEmail(userEntity.getEmail());
        userDto.setName(userEntity.getName());
        userDto.setCountry(userEntity.getCountry());
        userDto.setCity(userEntity.getCity());
        userDto.setStreet(userEntity.getStreet());
        userDto.setNumber(userEntity.getNumber());
        userDto.setZip(userEntity.getZip());
        userDto.setRole(userEntity.getRole());
        return userDto;
    }

}
