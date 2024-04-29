package com.webshop.shop.service;

import java.util.List;

import com.webshop.shop.dto.OrderCompanyDto;
import com.webshop.shop.dto.OrderUserDto;
import com.webshop.shop.models.Cart;

public interface OrderService {
    int createUserOrder();

    void createCompanyOrder(Cart cart);

    OrderUserDto getUserOrder(int orderId);

    List<OrderUserDto> getUserOrders();

    List<OrderCompanyDto> getCompanyOrders();

    OrderCompanyDto getCompanyOrder(int id);
}
