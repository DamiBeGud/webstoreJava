package com.webshop.shop.dto.response;

import java.util.List;

import com.webshop.shop.dto.CartDto;
import com.webshop.shop.dto.ProductDto;
import com.webshop.shop.models.Cart;
import com.webshop.shop.models.CartProduct;

import lombok.Data;

@Data
public class CartPageResponse {
    List<ProductDto> productsDto;
    List<CartProduct> cartProducts;
    CartDto cart;
}
