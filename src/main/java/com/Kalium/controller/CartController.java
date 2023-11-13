package com.Kalium.controller;

import com.Kalium.model.orderEntities.OrderAddBindingModel;
import com.Kalium.model.productEntities.ProductDTO;
import com.Kalium.service.CartService;
import com.fasterxml.jackson.databind.deser.std.MapDeserializer;
import jakarta.validation.Valid;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.UUID;

@Controller
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(value = "/products/addToCart/{productId}")
    public ModelAndView addToCart(@PathVariable UUID productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView("products");
        if (authentication != null && authentication.isAuthenticated()) {
            boolean isAdded = cartService.addToCart(productId);

            if (isAdded) {
                modelAndView.setViewName("redirect:/products");
                modelAndView.addObject("successMessage", "Product added to cart successfully");
            }
        } else {
            return new ModelAndView("redirect:/login");
        }
        return modelAndView;
    }

    @PostMapping(value = "/products/removeFromCart/{productId}")
    public ModelAndView removeFromCart(@PathVariable UUID productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            cartService.removeFromCart(productId);
        }
        return new ModelAndView("redirect:/shoppingCart");
    }

    @GetMapping("/shoppingCart")
    public ModelAndView showCart() {

        Map<ProductDTO, Integer> shoppingCartProducts = cartService.getUserCartProducts();

        return new ModelAndView("shopping-cart", "products", shoppingCartProducts);
    }
}
