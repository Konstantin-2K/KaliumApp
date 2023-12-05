package com.Kalium.controller;

import com.Kalium.service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @Test
    void addToCart_WhenNotAuthenticated_ReturnsRedirectToLogin() {
        UUID productId = UUID.randomUUID();
        SecurityContextHolder.clearContext();

        ModelAndView modelAndView = cartController.addToCart(productId);

        assertEquals("redirect:/login", modelAndView.getViewName());
    }
}
