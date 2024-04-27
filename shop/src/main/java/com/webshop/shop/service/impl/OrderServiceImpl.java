package com.webshop.shop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webshop.shop.dto.OrderUserDto;
import com.webshop.shop.dto.ProductDto;
import com.webshop.shop.dto.UserDto;
import com.webshop.shop.models.Cart;
import com.webshop.shop.models.OrderCompany;
import com.webshop.shop.models.OrderUser;
import com.webshop.shop.models.Product;
import com.webshop.shop.repository.CartRepository;
import com.webshop.shop.repository.OrderCompanyRepository;
import com.webshop.shop.repository.OrderUserRepository;
import com.webshop.shop.service.CartService;
import com.webshop.shop.service.OrderService;
import com.webshop.shop.service.ProductService;
import com.webshop.shop.service.UserService;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderUserRepository orderUserRepository;
    private OrderCompanyRepository orderCompanyRepository;
    private CartService cartService;
    private UserService userService;
    private CartRepository cartRepository;
    private ProductService productService;

    @Autowired
    public OrderServiceImpl(
            OrderUserRepository orderUserRepository,
            OrderCompanyRepository orderCompanyRepository,
            CartService cartService,
            UserService userService,
            CartRepository cartRepository,
            ProductService productService) {
        this.orderUserRepository = orderUserRepository;
        this.orderCompanyRepository = orderCompanyRepository;
        this.cartService = cartService;
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    @Override
    public int createUserOrder() {
        UserDto user = userService.getUser();
        Cart cart = cartService.getCartForOrderService(user.getId(), true);
        OrderUser order = new OrderUser();
        order.setCart(cart);
        order.setUserId(cart.getUserId());
        createCompanyOrder(cart);
        OrderUser newOrder = orderUserRepository.save(order);
        cartService.deactivateCart(cart.getId());

        return newOrder.getId();
    }

    @Override
    public void createCompanyOrder(Cart cart) {
        List<Integer> companyIds = cart.getCart().stream().map(product -> {
            return product.getCompanyId();
        }).distinct().collect(Collectors.toList());
        for (int company : companyIds) {
            OrderCompany newOrder = new OrderCompany();
            List<Product> products = cart.getCart().stream()
                    .filter(product -> product.getCompanyId() == company)
                    .map(product -> {
                        return productService.getOneProductByIdForOrderService(product.getProductId());
                    })
                    .collect(Collectors.toList());
            newOrder.setOrderedProducts(products);
            newOrder.setCompanyId(company);
            newOrder.setUserId(cart.getUserId());
            orderCompanyRepository.save(newOrder);
        }
    }

    private OrderUserDto mapToDtoOrderUser(OrderUser orderUser) {
        OrderUserDto orderUserDto = new OrderUserDto();
        orderUserDto.setId(orderUser.getId());
        orderUserDto.setUserId(orderUser.getUserId());
        orderUserDto.setDate(orderUser.getDate());
        orderUserDto.setCart(orderUser.getCart());
        return orderUserDto;
    }

}
