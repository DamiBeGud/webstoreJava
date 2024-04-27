package com.webshop.shop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webshop.shop.dto.CartDto;
import com.webshop.shop.dto.ProductDto;
import com.webshop.shop.dto.response.CartPageResponse;
import com.webshop.shop.exceptions.ProductNotFoundException;
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
import com.webshop.shop.service.ProductService;

@Service
public class CartServiceImpl implements CartService{

    private CartRepository cartRepository;
    private CartProductRepository cartProductRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private ProductService productService;
    @Autowired
    public CartServiceImpl(
        ProductRepository productRepository,
        CartRepository cartRepository,
        CartProductRepository cartProductRepository,
        UserRepository userRepository,
        ProductService productService) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartProductRepository = cartProductRepository;
        this.userRepository = userRepository;
        this.productService = productService;
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
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product was not found"));

        CartProduct cartProduct = new CartProduct();
        cartProduct.setCompanyId(product.getUserId());
        cartProduct.setProductId(productId);
        cartProduct.setQty(1);

        cart.getCart().add(cartProduct);

        cartRepository.save(cart);
    }

    @Override
    public CartPageResponse getShopingCart() {
        CartPageResponse cartPageResponse =  new CartPageResponse();
        Cart cart = getCart();
        CartDto cartDto = mapToDtoCart(cart);
        List<CartProduct> cartProducts = cart.getCart();
        List<ProductDto> products = cartProducts.stream().map(cp -> {
            ProductDto productDto = productService.getOneProductById(cp.getProductId());
            productDto.setCartProductId(cp.getId());
            return productDto;
        }).collect(Collectors.toList()); 
        double total = 0;
        for(ProductDto product : products){
            total = total + product.getPrice();
        }
        cartDto.setTotal(total);
        cartPageResponse.setCartProducts(cartProducts);
        cartPageResponse.setProductsDto(products);
        cartPageResponse.setCart(cartDto);
        System.out.println("Shoping cart" + cart);
        System.out.println("Products list" + products);
        return cartPageResponse;
    }


    private CartDto mapToDtoCart(Cart cart){
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setActive(cart.getActive());
        cartDto.setCart(cart.getCart());
        cartDto.setUserId(cart.getUserId());
        return cartDto;
    }
}
