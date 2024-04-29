package com.webshop.shop.controllers.api.order.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.webshop.shop.dto.OrderCompanyDto;
import com.webshop.shop.dto.ReviewDto;
import com.webshop.shop.service.OrderService;
import com.webshop.shop.service.ReviewService;

@RestController
@RequestMapping("/api/v1/orders/")
public class OrdersControllerRest {
    private OrderService orderService;

    public OrdersControllerRest(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("{id}")
    public ResponseEntity createReview(@PathVariable int id) {
        System.out.println(id);
        return new ResponseEntity<>(orderService.getCompanyOrder(id), HttpStatus.OK);
    }

}
