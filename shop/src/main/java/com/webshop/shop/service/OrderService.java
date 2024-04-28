package com.webshop.shop.service;

import com.webshop.shop.dto.OrderUserDto;
import com.webshop.shop.models.Cart;

public interface OrderService {
    int createUserOrder();

    void createCompanyOrder(Cart cart);

    OrderUserDto getUserOrder(int orderId);
}
