package com.webshop.shop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webshop.shop.models.Cart;
import com.webshop.shop.models.CartProduct;
import com.webshop.shop.models.Product;
import com.webshop.shop.models.UserEntity;
import com.webshop.shop.repository.CartProductRepository;
import com.webshop.shop.repository.CartRepository;
import com.webshop.shop.repository.ProductRepository;
import com.webshop.shop.repository.UserRepository;
import com.webshop.shop.security.SecurityUtil;
import com.webshop.shop.service.CartService;

@Service
public class CartServiceImpl implements CartService{

    private CartRepository cartRepository;
    private CartProductRepository cartProductRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    @Autowired
    public CartServiceImpl(ProductRepository productRepository, CartRepository cartRepository, CartProductRepository cartProductRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartProductRepository = cartProductRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Cart createCart(int id) {
        Cart cart = new Cart();
        cart.setUserId(id);
        cart.setCart(new ArrayList<>());
        Cart newCart = cartRepository.save(cart);
        return newCart;
    }

    @Override
    public Cart getCart() {
        
        String email = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByEmail(email);
        int id = user.getId();

        List<Cart> carts = cartRepository.findByUserId(id);
        Cart cart = new Cart();

        if(carts.size() > 0){
           Cart foundCart = carts.stream().filter(c -> c.getActive() == true).findFirst().orElseThrow();
           if(foundCart !=null){
            cart.setId(foundCart.getId());
            cart.setUserId(foundCart.getUserId());
            cart.setCart(foundCart.getCart());
            cart.setActive(foundCart.getActive());
           }else{
            Cart newCart = createCart(id);
            cart.setId(newCart.getId());
            cart.setUserId(newCart.getUserId());
            cart.setActive(newCart.getActive());
            cart.setCart(newCart.getCart());
           }
        }else{
            Cart newCart = createCart(id);
            cart.setId(newCart.getId());
            cart.setUserId(newCart.getUserId());
            cart.setActive(newCart.getActive());
            cart.setCart(newCart.getCart());
        }
        System.out.println(cart);
     return cart;   
    }


    @Override
    public void addProductToCart(int userId, int cartId, int productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        CartProduct cartProduct = new CartProduct();
        cartProduct.setCompanyId(product.getUserId());
        cartProduct.setProductId(productId);
        cartProduct.setQty(1);

        cart.getCart().add(cartProduct);

        cartRepository.save(cart);
    }
}
