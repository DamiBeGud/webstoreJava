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

/**
 * Service implementation for user management.
 * This service handles user registration, retrieval, and other user-related
 * operations.
 */
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    /**
     * Constructs a new UserServiceImpl with necessary dependencies.
     *
     * @param userRepository  the repository handling user data
     * @param passwordEncoder a password encoder for encoding plain text passwords
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Saves a new user to the database.
     *
     * @param registerDto the data transfer object containing user registration data
     * @param role        the role to be assigned to the new user
     * @return the ID of the newly created user
     */
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
        user.setCity(registerDto.getCity());
        userRepository.save(user);

        return user.getId();
    }

    /**
     * Finds a user by email.
     *
     * @param email the email to search for
     * @return the found UserEntity or null if no user is found
     */
    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Retrieves a user by ID and converts it to a UserDto.
     *
     * @param id the ID of the user
     * @return a data transfer object containing user data
     */
    @Override
    public UserDto getUserById(int id) {
        UserEntity userEntity = userRepository.getById(id);
        return mapToDtoUser(userEntity);
    }

    /**
     * Retrieves the currently authenticated user.
     *
     * @return a data transfer object containing the authenticated user's data
     */
    @Override
    public UserDto getUser() {
        String email = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByEmail(email);
        System.out.println(user);
        return mapToDtoUser(user);
    }

    /**
     * Converts a UserEntity instance to a UserDto.
     *
     * @param userEntity the user entity to convert
     * @return the converted user data transfer object
     */
    private UserDto mapToDtoUser(UserEntity userEntity) {
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
