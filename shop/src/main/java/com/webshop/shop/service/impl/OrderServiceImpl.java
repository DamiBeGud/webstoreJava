package com.webshop.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.webshop.shop.dto.OrderUserDto;
import com.webshop.shop.dto.UserDto;
import com.webshop.shop.models.Cart;
import com.webshop.shop.models.OrderUser;
import com.webshop.shop.repository.OrderCompanyRepository;
import com.webshop.shop.repository.OrderUserRepository;
import com.webshop.shop.service.CartService;
import com.webshop.shop.service.OrderService;
import com.webshop.shop.service.UserService;

public class OrderServiceImpl implements OrderService{
    private OrderUserRepository orderUserRepository;
    private OrderCompanyRepository orderCompanyRepository;
    private CartService cartService;
    private UserService userService;
    @Autowired
    public OrderServiceImpl
    (
        OrderUserRepository orderUserRepository,
        OrderCompanyRepository orderCompanyRepository,
        CartService cartService,
        UserService userService
        ) {
        this.orderUserRepository = orderUserRepository;
        this.orderCompanyRepository = orderCompanyRepository;
        this.cartService = cartService;
        this.userService = userService;
    }
    @Override
    public OrderUserDto createUserOrder() {
        // UserDto user = userService.getUser();
        OrderUser order = new OrderUser();
        Cart cart = cartService.getCart();

        order.setCart(cart);
        order.setUserId(cart.getUserId());

        OrderUser newOrder = orderUserRepository.save(order);
        cartService.deactivateCart(cart.getId());

        return mapToDtoOrderUser(newOrder);
    }

    private OrderUserDto mapToDtoOrderUser(OrderUser orderUser){
        OrderUserDto orderUserDto = new OrderUserDto();
        orderUserDto.setId(orderUser.getId());
        orderUserDto.setUserId(orderUser.getUserId());
        orderUserDto.setDate(orderUser.getDate());
        orderUserDto.setCart(orderUser.getCart());
        return orderUserDto;
    }
    

}
