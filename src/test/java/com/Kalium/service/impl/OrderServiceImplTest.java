package com.Kalium.service.impl;

import com.Kalium.model.orderEntities.Order;
import com.Kalium.model.orderEntities.OrderAddBindingModel;
import com.Kalium.model.productEntities.Product;
import com.Kalium.model.userEntities.User;
import com.Kalium.repo.OrderRepository;
import com.Kalium.repo.ProductRepository;
import com.Kalium.repo.UserRepository;
import com.Kalium.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserDetails userDetails;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void addOrder_ShouldNotAddOrder_WhenUserIsNotAuthenticated() {
        OrderAddBindingModel orderAddBindingModel = new OrderAddBindingModel();

        when(authentication.getPrincipal()).thenReturn("anonymousUser");

        boolean result = orderService.addOrder(orderAddBindingModel);

        assertFalse(result);
        verify(productRepository, never()).save(any(Product.class));
        verify(orderRepository, never()).save(any(Order.class));
        verify(userRepository, never()).save(any(User.class));
    }
}
