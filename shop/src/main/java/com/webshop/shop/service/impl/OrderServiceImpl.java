package com.webshop.shop.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Comparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webshop.shop.dto.OrderCompanyDto;
import com.webshop.shop.dto.OrderUserDto;
import com.webshop.shop.dto.ProductDto;
import com.webshop.shop.dto.UserDto;
import com.webshop.shop.models.Cart;
import com.webshop.shop.models.CartProduct;
import com.webshop.shop.models.OrderCompany;
import com.webshop.shop.models.OrderUser;
import com.webshop.shop.models.Product;
import com.webshop.shop.models.ProductOrderModel;
import com.webshop.shop.models.UserEntity;
import com.webshop.shop.repository.CartRepository;
import com.webshop.shop.repository.OrderCompanyRepository;
import com.webshop.shop.repository.OrderUserRepository;
import com.webshop.shop.repository.ProductOrderModelRepository;
import com.webshop.shop.service.CartService;
import com.webshop.shop.service.OrderService;
import com.webshop.shop.service.ProductService;
import com.webshop.shop.service.UserService;
import com.webshop.shop.util.TupleInteger;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderUserRepository orderUserRepository;
    private OrderCompanyRepository orderCompanyRepository;
    private CartService cartService;
    private UserService userService;
    private CartRepository cartRepository;
    private ProductService productService;
    private ProductOrderModelRepository productOrderModelRepository;

    @Autowired
    public OrderServiceImpl(
            OrderUserRepository orderUserRepository,
            OrderCompanyRepository orderCompanyRepository,
            CartService cartService,
            UserService userService,
            CartRepository cartRepository,
            ProductService productService,
            ProductOrderModelRepository productOrderModelRepository) {
        this.orderUserRepository = orderUserRepository;
        this.orderCompanyRepository = orderCompanyRepository;
        this.cartService = cartService;
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.productOrderModelRepository = productOrderModelRepository;
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

    // Has to be updated to dto same as users order
    @Override
    public void createCompanyOrder(Cart cart) {
        List<Integer> companyIds = cart.getCart().stream().map(product -> {
            return product.getCompanyId();
        }).distinct().collect(Collectors.toList());
        List<TupleInteger> prodIdQty = cart.getCart().stream().map(c -> {
            return new TupleInteger(c.getProductId(), c.getQty());
        }).collect(Collectors.toList());

        for (int company : companyIds) {
            OrderCompany newOrder = new OrderCompany();
            List<ProductOrderModel> products = cart.getCart().stream()
                    .filter(product -> product.getCompanyId() == company)
                    .map(product -> {
                        Product prodFetch = productService.getOneProductByIdForOrderService(product.getProductId());
                        Product prod = prodFetch;
                        ProductOrderModel orderProd = productToOrderProduct(prod);
                        orderProd.setQty(product.getQty());
                        productOrderModelRepository.save(orderProd);
                        return orderProd;
                    })
                    .collect(Collectors.toList());
            double total = 0;
            for (ProductOrderModel product : products) {
                total = total + product.getPrice();
            }

            newOrder.setOrderedProducts(products);
            newOrder.setCompanyId(company);
            newOrder.setUserId(cart.getUserId());
            newOrder.setTotal(total);
            orderCompanyRepository.save(newOrder);
            for (TupleInteger tuple : prodIdQty) {
                productService.updateStockOrder(tuple.getFirst(), tuple.getSecond());
            }
        }
    }

    private ProductOrderModel productToOrderProduct(Product product) {
        ProductOrderModel productOrderModel = new ProductOrderModel();
        productOrderModel.setProductid(product.getId());
        productOrderModel.setName(product.getName());
        productOrderModel.setDescription(product.getDescription());
        if (product.getDiscount() == true) {
            productOrderModel.setPrice(product.getDiscountPrice());
        } else {
            productOrderModel.setPrice(product.getPrice());
        }
        productOrderModel.setUserId(product.getUserId());
        productOrderModel.setStock(product.getStock());
        productOrderModel.setCategory(product.getCategory());
        productOrderModel.setSubCategory(product.getSubCategory());
        productOrderModel.setImage(product.getImage());
        return productOrderModel;
    }

    private OrderUserDto mapToDtoOrderUser(OrderUser orderUser) {
        OrderUserDto orderUserDto = new OrderUserDto();
        orderUserDto.setId(orderUser.getId());
        orderUserDto.setUserId(orderUser.getUserId());
        orderUserDto.setDate(orderUser.getDate());
        orderUserDto.setCart(orderUser.getCart());
        return orderUserDto;
    }

    @Override
    public OrderUserDto getUserOrder(int orderId) {
        OrderUser order = orderUserRepository.findById(orderId).orElseThrow();
        return mapToDtoOrderUser(order);
    }

    @Override
    public List<OrderUserDto> getUserOrders() {
        UserDto user = userService.getUser();

        List<OrderUser> orders = orderUserRepository.findAllByUserId(user.getId());
        List<OrderUserDto> ordersDto = new ArrayList<>();
        for (OrderUser order : orders) {
            OrderUserDto orderDto = new OrderUserDto();
            Cart cart = order.getCart();
            List<ProductDto> products = cart.getCart().stream().map(c -> {
                Product produ = productService.getOneProductByIdForOrderService(c.getProductId());
                ProductDto product = mapToDtoProduct(produ);
                product.setQty(c.getQty());
                return product;
            }).collect(Collectors.toList());
            orderDto.setId(order.getId());
            orderDto.setCart(cart);
            orderDto.setDate(order.getDate());
            orderDto.setOrderedProducts(products);
            orderDto.setTotal(0);
            orderDto.setUserId(order.getUserId());
            ordersDto.add(orderDto);
        }
        return ordersDto;
    }

    private ProductDto mapToDtoProduct(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setUserId(product.getUserId());
        productDto.setStock(product.getStock());
        productDto.setCategory(product.getCategory());
        productDto.setSubCategory(product.getSubCategory());
        productDto.setDiscount(product.getDiscount());
        productDto.setDiscountPrice(product.getDiscountPrice());
        productDto.setImage(product.getImage());
        return productDto;
    }

    @Override
    public List<OrderCompanyDto> getCompanyOrders() {
        UserDto user = userService.getUser();
        List<OrderCompanyDto> orders = orderCompanyRepository.findAllByCompanyId(user.getId()).stream()
                .map(order -> {
                    OrderCompanyDto orderDto = mapToDtoCompanyOrder(order);
                    orderDto.setCustomer(userService.getUserById(orderDto.getUserId()));
                    return orderDto;
                }).collect(Collectors.toList());
        Collections.sort(orders, Comparator.comparing(OrderCompanyDto::getId).reversed());
        return orders;
    }

    private OrderCompanyDto mapToDtoCompanyOrder(OrderCompany orderCompany) {
        OrderCompanyDto orderCompanyDto = new OrderCompanyDto();
        orderCompanyDto.setId(orderCompany.getId());
        orderCompanyDto.setUserId(orderCompany.getUserId());
        orderCompanyDto.setCompanyId(orderCompany.getCompanyId());
        orderCompanyDto.setDate(orderCompany.getDate());
        orderCompanyDto.setDateShipped(orderCompany.getDateShipped());
        orderCompanyDto.setStatus(orderCompany.getStatus());
        orderCompanyDto.setOrderedProducts(orderCompany.getOrderedProducts());
        orderCompanyDto.setTotal(orderCompany.getTotal());

        return orderCompanyDto;

    }

    @Override
    public OrderCompanyDto getCompanyOrder(int id) {
        System.out.println("ORDER SERVICE ID" + id);
        OrderCompany order = orderCompanyRepository.findById(id).orElseThrow();
        System.out.println("ORDER SERVICE DEBUG" + order);
        return mapToDtoCompanyOrder(order);
    }

    @Override
    public OrderCompanyDto shipOrder(int orderId) {
        OrderCompany order = orderCompanyRepository.findById(orderId).orElseThrow();
        order.setStatus(true);
        LocalDate date = LocalDate.now();
        order.setDateShipped(date);
        OrderCompany shipedOrder = orderCompanyRepository.save(order);

        return mapToDtoCompanyOrder(shipedOrder);
    }

}
