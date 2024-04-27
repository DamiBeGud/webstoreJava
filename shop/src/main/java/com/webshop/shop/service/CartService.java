package com.webshop.shop.service;

import com.webshop.shop.dto.CartDto;
import com.webshop.shop.dto.CartProductDto;
import com.webshop.shop.models.Cart;

public interface CartService {
    Cart createCart(int id);

    Cart getCart();

    void addProductToCart(int userId, int cartId, int productId);

    int removeProductFromCart(int cartId, int cartProductId);

    CartDto getShopingCart();

    int getNumberOfProductsInCart();

    CartProductDto updateQty(int cartProductId, int qty);
}
