package com.webshop.shop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webshop.shop.dto.CartDto;
import com.webshop.shop.dto.CartProductDto;
import com.webshop.shop.dto.ProductDto;
import com.webshop.shop.exceptions.ProductNotFoundException;
import com.webshop.shop.exceptions.UpdateQtyException;
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

import jakarta.transaction.Transactional;

/**
 * Provides the implementation of cart service managing user carts and
 * interactions with products within these carts.
 */
@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private CartProductRepository cartProductRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private ProductService productService;

    /**
     * Constructs the cart service with necessary repository and service injections.
     */
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

    /**
     * Creates a new cart for a user based on the user's ID.
     * 
     * @param id the user's ID.
     * @return the newly created cart.
     */
    @Override
    public Cart createCart(int id) {
        Cart cart = new Cart();
        cart.setUserId(id);
        cart.setCart(new ArrayList<>());
        Cart newCart = cartRepository.save(cart);
        return newCart;
    }

    /**
     * Retrieves the current active cart for a session user, creating a new one if
     * necessary.
     * 
     * @return the active cart.
     */
    @Override
    public Cart getCart() {

        String email = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByEmail(email);
        int id = user.getId();

        List<Cart> carts = cartRepository.findByUserId(id);

        Cart cart = new Cart();

        if (carts.size() > 0) {
            Optional<Cart> foundCart = carts.stream().filter(c -> c.getActive() == true).findFirst();
            if (foundCart.isPresent()) {
                cart.setId(foundCart.get().getId());
                cart.setUserId(foundCart.get().getUserId());
                cart.setCart(foundCart.get().getCart());
                cart.setActive(foundCart.get().getActive());
            } else {
                Cart newCart = createCart(id);
                cart.setId(newCart.getId());
                cart.setUserId(newCart.getUserId());
                cart.setActive(newCart.getActive());
                cart.setCart(newCart.getCart());
            }
        } else {
            Cart newCart = createCart(id);
            cart.setId(newCart.getId());
            cart.setUserId(newCart.getUserId());
            cart.setActive(newCart.getActive());
            cart.setCart(newCart.getCart());
        }
        System.out.println(cart);
        return cart;
    }

    /**
     * Adds a product to the user's cart.
     * 
     * @param userId    the user's ID.
     * @param cartId    the cart's ID.
     * @param productId the product's ID to add.
     */
    @Override
    public void addProductToCart(int userId, int cartId, int productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product was not found"));

        CartProduct cartProduct = new CartProduct();
        cartProduct.setCompanyId(product.getUserId());
        cartProduct.setProductId(productId);
        cartProduct.setQty(1);

        cart.getCart().add(cartProduct);

        cartRepository.save(cart);
    }

    /**
     * Retrieves the shopping cart details as a DTO, including all products within
     * the cart.
     * 
     * @return the detailed cart DTO.
     */
    @Override
    public CartDto getShopingCart() {
        Cart cart = getCart();
        CartDto cartDto = mapToDtoCart(cart);

        List<CartProduct> cartProducts = cart.getCart();

        List<CartProductDto> cartProductDtos = cartProducts.stream().map(cp -> {
            ProductDto productDto = productService.getOneProductById(cp.getProductId());
            CartProductDto cartProductDto = productDtoToCartProductDto(productDto);
            cartProductDto.setId(cp.getId());
            cartProductDto.setQty(cp.getQty());
            cartProductDto.setPrice(cartProductDto.getPrice() * cartProductDto.getQty());
            return cartProductDto;
        }).collect(Collectors.toList());
        double total = 0;
        for (CartProductDto cartProductDto : cartProductDtos) {
            double price = cartProductDto.getPrice();
            total = total + price;
        }

        cartDto.setCart(cartProductDtos);
        cartDto.setTotal(total);
        System.out.println("CartDto sent to FE" + cartDto);
        return cartDto;
    }

    private CartDto mapToDtoCart(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setActive(cart.getActive());
        // cartDto.setCart(cart.getCart());
        cartDto.setUserId(cart.getUserId());
        return cartDto;
    }

    private CartProductDto productDtoToCartProductDto(ProductDto productDto) {
        CartProductDto cartProductDto = new CartProductDto();
        cartProductDto.setProductId(productDto.getId());
        cartProductDto.setName(productDto.getName());
        cartProductDto.setCompanyId(productDto.getUserId());
        cartProductDto.setImage(productDto.getImage());
        if (productDto.getDiscount()) {
            cartProductDto.setPrice(productDto.getDiscountPrice());
        } else {
            cartProductDto.setPrice(productDto.getPrice());
        }
        return cartProductDto;
    }

    /**
     * Removes a product from the user's cart.
     * 
     * @param cartId        the ID of the cart.
     * @param cartProductId the ID of the cart product to remove.
     * @return the ID of the removed cart product.
     */
    @Override
    public int removeProductFromCart(int cartId, int cartProductId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        System.out.println("Cart remove:" + cart);
        List<CartProduct> cartProducts = cart.getCart();

        cartProducts.removeIf(cp -> cp.getId() == cartProductId);
        cart.setCart(cartProducts);
        cartRepository.save(cart);
        cartProductRepository.deleteById(cartProductId);

        return cartProductId;
    }

    /**
     * Returns the number of products in the current session user's cart.
     * 
     * @return the count of products in the cart.
     */
    @Override
    public int getNumberOfProductsInCart() {
        Cart cart = getCart();
        int numberOfProductsInCart = cart.getCart().size();
        return numberOfProductsInCart;
    }

    /**
     * Updates the quantity of a product in the cart and recalculates the price.
     * 
     * @param cartProductId the ID of the cart product to update.
     * @param qty           the new quantity.
     * @return the updated cart product DTO.
     */
    @Override
    public CartProductDto updateQty(int cartProductId, int qty) {
        CartProduct cartProduct = cartProductRepository.findById(cartProductId).orElseThrow();
        Product checkProduct = productRepository.findById(cartProduct.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
        if (checkProduct.getStock() < qty) {
            throw new UpdateQtyException("Maximal Qty is" + checkProduct.getStock());
        }
        CartProductDto cartProductDto = new CartProductDto();
        cartProduct.setQty(qty);
        cartProductRepository.save(cartProduct);

        double updatedPrice = 0;

        ProductDto product = productService.getOneProductById(cartProduct.getProductId());
        if (product.getDiscount() == true) {
            updatedPrice = product.getDiscountPrice() * qty;
        } else {
            updatedPrice = product.getPrice() * qty;
        }

        cartProductDto.setQty(qty);
        cartProductDto.setId(cartProductId);
        cartProductDto.setPrice(updatedPrice);

        return cartProductDto;
    }

    /**
     * Deactivates a user's cart, marking it as inactive.
     * 
     * @param cartId the ID of the cart to deactivate.
     */
    @Override
    public void deactivateCart(int cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        cart.setActive(false);
        cartRepository.save(cart);
    }

    /**
     * Retrieves the cart for order processing based on the user ID and activity
     * status.
     * 
     * @param userId the user's ID.
     * @param active the activity status to filter by.
     * @return the cart matching the criteria.
     */
    @Override
    public Cart getCartForOrderService(int userId, Boolean active) {
        Cart cart = cartRepository.findFirstByUserIdAndActive(userId, active);
        return cart;
    }

    /**
     * Removes all cart products associated with a discontinued product from all
     * carts.
     * 
     * @param productId the ID of the discontinued product.
     */
    @Override
    @Transactional
    public void removeDiscontinuedProductFromCarts(int productId) {
        List<CartProduct> cartProducts = cartProductRepository.findAllByProductId(productId);
        for (CartProduct product : cartProducts) {
            cartRepository.deleteAllByCartProductId(product.getId());
        }
    }

}
