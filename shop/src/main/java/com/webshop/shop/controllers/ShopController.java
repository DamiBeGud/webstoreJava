package com.webshop.shop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.webshop.shop.dto.ProductDto;
import com.webshop.shop.models.Cart;
import com.webshop.shop.security.SecurityUtil;
import com.webshop.shop.service.CartService;
import com.webshop.shop.service.CategorysService;
import com.webshop.shop.service.ProductService;
import com.webshop.shop.service.ReviewService;
import com.webshop.shop.service.UserService;

@Controller
public class ShopController {

    private ProductService productService;
    private ReviewService reviewService;
    private UserService userService;
    private CartService cartService;
    private CategorysService categorysService;

    @Autowired
    public ShopController(
            ProductService productService,
            ReviewService reviewService,
            UserService userService,
            CartService cartService,
            CategorysService categorysService) {
        this.productService = productService;
        this.reviewService = reviewService;
        this.userService = userService;
        this.cartService = cartService;
        this.categorysService = categorysService;
    }

    @GetMapping("/")
    public String getShopPage(Model model) {
        String email = SecurityUtil.getSessionUser();
        if (email != null) {
            model.addAttribute("user", userService.getUser());
            Cart cart = cartService.getCart();
            model.addAttribute("cart", cart);
            model.addAttribute("productsInCart", cartService.getNumberOfProductsInCart());
        }
        model.addAttribute("specialDeals", productService.getSpecialDealProducts());
        model.addAttribute("electronics", productService.getProductsByCategory(1));
        model.addAttribute("homeAndKitchen", productService.getProductsByCategory(2));
        model.addAttribute("fashion", productService.getProductsByCategory(3));
        return "shop";
    }

    @GetMapping("/shop")
    public String getProducts(Model model,
            @RequestParam(required = false, name = "category") Integer categoryId,
            @RequestParam(required = false, name = "sort") String sortString,
            @RequestParam(required = false, name = "subcategory") Integer subCategoryId,
            @RequestParam(required = false, name = "from") Double from,
            @RequestParam(required = false, name = "to") Double to) {

        model.addAttribute("products", filterChain(
                categoryId,
                sortString,
                subCategoryId,
                from,
                to));

        model.addAttribute("category", categorysService.getAllCategorys());
        model.addAttribute("subCategory", categorysService.getAllSubCategorys());
        String email = SecurityUtil.getSessionUser();
        if (email != null) {
            model.addAttribute("user", userService.getUser());
            Cart cart = cartService.getCart();
            model.addAttribute("cart", cart);
            model.addAttribute("productsInCart", cartService.getNumberOfProductsInCart());
        }

        return "allProducts";
    }

    @GetMapping("/shop/search")
    public String getSearchProducts(Model model, @RequestParam("search") String searchString) {

        model.addAttribute("products", productService.searchForProducts(searchString));
        model.addAttribute("category", categorysService.getAllCategorys());
        model.addAttribute("subCategory", categorysService.getAllSubCategorys());
        String email = SecurityUtil.getSessionUser();
        if (email != null) {
            model.addAttribute("user", userService.getUser());
            Cart cart = cartService.getCart();
            model.addAttribute("cart", cart);
            model.addAttribute("productsInCart", cartService.getNumberOfProductsInCart());
        }

        return "allProducts";
    }

    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.getOneProductById(id));
        model.addAttribute("reviews", reviewService.findReviewsByProductId(id));
        String email = SecurityUtil.getSessionUser();
        if (email != null) {
            model.addAttribute("user", userService.getUser());
            model.addAttribute("productsInCart", cartService.getNumberOfProductsInCart());
        }

        return "product";
    }

    @GetMapping("/about")
    public String getAboutUsPage(Model model) {
        String email = SecurityUtil.getSessionUser();
        if (email != null) {
            model.addAttribute("user", userService.getUser());
            model.addAttribute("productsInCart", cartService.getNumberOfProductsInCart());
        }
        return "aboutUs";
    }

    @GetMapping("/contact")
    public String getContactUsPage(Model model) {
        String email = SecurityUtil.getSessionUser();
        if (email != null) {
            model.addAttribute("user", userService.getUser());
            model.addAttribute("productsInCart", cartService.getNumberOfProductsInCart());
        }
        return "contactUs";
    }

    @GetMapping("/faq")
    public String getFAQPage(Model model) {
        String email = SecurityUtil.getSessionUser();
        if (email != null) {
            model.addAttribute("user", userService.getUser());
            model.addAttribute("productsInCart", cartService.getNumberOfProductsInCart());
        }
        return "faq";
    }

    @GetMapping("/termsandconditions")
    public String getTermsAndConditionsPage(Model model) {
        String email = SecurityUtil.getSessionUser();
        if (email != null) {
            model.addAttribute("user", userService.getUser());
            model.addAttribute("productsInCart", cartService.getNumberOfProductsInCart());
        }
        return "termsAndConditions";
    }

    private List<ProductDto> filterChain(
            Integer categoryId,
            String sortString,
            Integer subCategoryId,
            Double from,
            Double to) {
        List<ProductDto> products;
        if (categoryId == null && subCategoryId == null) {
            products = productService.getAllProductsShop();
        } else {
            if (categoryId != null) {
                products = productService.getProductsByCategory(categoryId);
            } else {
                products = productService.getProductsBySubCategory(subCategoryId);
            }
        }
        if (sortString != null) {
            products = productService.sortProducts(sortString, products);
        }
        if (from != null || to != null) {
            if (from == null) {
                from = 0.0;
            }
            if (to == null) {
                to = 10000.0;
            }
            products = productService.filterProducts(from, to, products);
        }
        return products;
    }

}
