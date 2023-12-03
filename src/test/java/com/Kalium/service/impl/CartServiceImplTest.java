package com.Kalium.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.Kalium.model.productEntities.Product;
import com.Kalium.model.productEntities.ProductDTO;
import com.Kalium.model.userEntities.User;
import com.Kalium.repo.ProductRepository;
import com.Kalium.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

class CartServiceImplTest {

    private CartServiceImpl cartService;
    private UserRepository userRepositoryMock;
    private ProductRepository productRepositoryMock;

    @BeforeEach
    void setUp() {
        userRepositoryMock = mock(UserRepository.class);
        productRepositoryMock = mock(ProductRepository.class);
        cartService = new CartServiceImpl(userRepositoryMock, productRepositoryMock);
    }

    @Test
    void addToCart_WithAuthenticatedUser_ShouldAddToShoppingCart() {
        // Arrange
        Authentication authenticationMock = mock(Authentication.class);
        UserDetails userDetailsMock = mock(UserDetails.class);
        when(authenticationMock.isAuthenticated()).thenReturn(true);
        when(authenticationMock.getPrincipal()).thenReturn(userDetailsMock);
        when(userDetailsMock.getUsername()).thenReturn("testuser@example.com");

        SecurityContextHolder.getContext().setAuthentication(authenticationMock);

        UUID productId = UUID.randomUUID();
        Product product = new Product();
        product.setId(productId);
        when(productRepositoryMock.findById(productId)).thenReturn(Optional.of(product));

        User user = new User();
        when(userRepositoryMock.findByEmail("testuser@example.com")).thenReturn(Optional.of(user));

        boolean result = cartService.addToCart(productId);

        assertTrue(result);
        assertTrue(user.getShoppingCart().containsKey(product));
        assertEquals(1, user.getShoppingCart().get(product));

        verify(userRepositoryMock, times(1)).save(user);
    }

    @Test
    void addToCart_WithUnauthenticatedUser_ShouldNotAddToShoppingCart() {
        Authentication authenticationMock = mock(Authentication.class);
        when(authenticationMock.isAuthenticated()).thenReturn(false);

        SecurityContextHolder.getContext().setAuthentication(authenticationMock);

        UUID productId = UUID.randomUUID();

        boolean result = cartService.addToCart(productId);

        assertFalse(result);

        verify(userRepositoryMock, never()).save(any());
    }

    @Test
    void getUserCartProducts_WithAuthenticatedUser_ShouldReturnCartProducts() {
        Authentication authenticationMock = mock(Authentication.class);
        UserDetails userDetailsMock = mock(UserDetails.class);
        when(authenticationMock.isAuthenticated()).thenReturn(true);
        when(authenticationMock.getPrincipal()).thenReturn(userDetailsMock);
        when(userDetailsMock.getUsername()).thenReturn("testuser@example.com");

        SecurityContextHolder.getContext().setAuthentication(authenticationMock);

        User user = new User();
        UUID productId = UUID.randomUUID();
        Product product = new Product();
        product.setId(productId);
        user.getShoppingCart().put(product, 2);

        when(userRepositoryMock.findByEmail("testuser@example.com")).thenReturn(Optional.of(user));

        Map<ProductDTO, Integer> result = cartService.getUserCartProducts();

        assertNotNull(result);
        assertTrue(result.containsKey(ProductDTO.createFromProduct(product)));
        assertEquals(2, result.get(ProductDTO.createFromProduct(product)));
    }

    @Test
    void getUserCartProducts_WithUnauthenticatedUser_ShouldReturnEmptyCart() {
        Authentication authenticationMock = mock(Authentication.class);
        when(authenticationMock.isAuthenticated()).thenReturn(false);

        SecurityContextHolder.getContext().setAuthentication(authenticationMock);

        Map<ProductDTO, Integer> result = cartService.getUserCartProducts();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void removeFromCart_WithAuthenticatedUserAndExistingProduct_ShouldRemoveFromCart() {
        // Arrange
        Authentication authenticationMock = mock(Authentication.class);
        UserDetails userDetailsMock = mock(UserDetails.class);
        when(authenticationMock.isAuthenticated()).thenReturn(true);
        when(authenticationMock.getPrincipal()).thenReturn(userDetailsMock);
        when(userDetailsMock.getUsername()).thenReturn("testuser@example.com");

        SecurityContextHolder.getContext().setAuthentication(authenticationMock);

        UUID productId = UUID.randomUUID();
        Product product = new Product();
        product.setId(productId);

        User user = new User();
        user.getShoppingCart().put(product, 2);

        when(userRepositoryMock.findByEmail("testuser@example.com")).thenReturn(Optional.of(user));
        when(productRepositoryMock.findById(productId)).thenReturn(Optional.of(product));

        cartService.removeFromCart(productId);

        assertFalse(user.getShoppingCart().containsKey(product));
        verify(userRepositoryMock, times(1)).save(user);
    }

    @Test
    void removeFromCart_WithAuthenticatedUserAndNonExistingProduct_ShouldNotRemoveFromCart() {
        Authentication authenticationMock = mock(Authentication.class);
        UserDetails userDetailsMock = mock(UserDetails.class);
        when(authenticationMock.isAuthenticated()).thenReturn(true);
        when(authenticationMock.getPrincipal()).thenReturn(userDetailsMock);
        when(userDetailsMock.getUsername()).thenReturn("testuser@example.com");

        SecurityContextHolder.getContext().setAuthentication(authenticationMock);

        UUID productId = UUID.randomUUID();

        User user = new User();

        when(userRepositoryMock.findByEmail(any())).thenReturn(Optional.of(user));
        when(productRepositoryMock.findById(productId)).thenReturn(Optional.empty());

        cartService.removeFromCart(productId);

        assertTrue(user.getShoppingCart().isEmpty());
        verify(userRepositoryMock, never()).save(user);
    }

    @Test
    void removeFromCart_WithUnauthenticatedUser_ShouldNotRemoveFromCart() {
        Authentication authenticationMock = mock(Authentication.class);
        when(authenticationMock.isAuthenticated()).thenReturn(false);

        SecurityContextHolder.getContext().setAuthentication(authenticationMock);

        UUID productId = UUID.randomUUID();

        cartService.removeFromCart(productId);

        verify(userRepositoryMock, times(0)).save(any());
    }
}