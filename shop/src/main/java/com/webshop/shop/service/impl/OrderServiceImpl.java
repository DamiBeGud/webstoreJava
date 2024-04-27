package com.webshop.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webshop.shop.dto.OrderUserDto;
import com.webshop.shop.dto.UserDto;
import com.webshop.shop.models.Cart;
import com.webshop.shop.models.OrderUser;
import com.webshop.shop.repository.CartRepository;
import com.webshop.shop.repository.OrderCompanyRepository;
import com.webshop.shop.repository.OrderUserRepository;
import com.webshop.shop.service.CartService;
import com.webshop.shop.service.OrderService;
import com.webshop.shop.service.UserService;

@Service
public class OrderServiceImpl implements OrderService{
    private OrderUserRepository orderUserRepository;
    private OrderCompanyRepository orderCompanyRepository;
    private CartService cartService;
    private UserService userService;
    private CartRepository cartRepository;
    @Autowired
    public OrderServiceImpl
    (
        OrderUserRepository orderUserRepository,
        OrderCompanyRepository orderCompanyRepository,
        CartService cartService,
        UserService userService,
        CartRepository cartRepository
        ) {
        this.orderUserRepository = orderUserRepository;
        this.orderCompanyRepository = orderCompanyRepository;
        this.cartService = cartService;
        this.userService = userService;
        this.cartRepository = cartRepository;
    }
    @Override
    public int createUserOrder() {
        UserDto user = userService.getUser();
        Cart cart = cartService.getCartForOrderService(user.getId(), true);
        OrderUser order = new OrderUser();
        order.setCart(cart);
        order.setUserId(cart.getUserId());
        
        OrderUser newOrder = orderUserRepository.save(order);
        cartService.deactivateCart(cart.getId());
        
        return newOrder.getId();
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
