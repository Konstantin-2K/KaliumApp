package com.Kalium.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.Kalium.model.enums.CategoryEnum;
import com.Kalium.model.productEntities.Product;
import com.Kalium.model.productEntities.ProductAddBindingModel;
import com.Kalium.model.productEntities.ProductDTO;
import com.Kalium.model.userEntities.User;
import com.Kalium.repo.ProductRepository;
import com.Kalium.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class ProductServiceImplTest {

    private ProductServiceImpl productService;
    private ProductRepository productRepositoryMock;
    private UserRepository userRepositoryMock;

    @BeforeEach
    void setUp() {
        productRepositoryMock = mock(ProductRepository.class);
        userRepositoryMock = mock(UserRepository.class);
        productService = new ProductServiceImpl(productRepositoryMock, userRepositoryMock);
    }

    @Test
    void addProduct_WithAuthenticatedUserAndValidData_ShouldAddProduct() {
        Authentication authenticationMock = mock(Authentication.class);
        UserDetails userDetailsMock = mock(UserDetails.class);
        SecurityContext securityContextMock = mock(SecurityContext.class);

        when(authenticationMock.isAuthenticated()).thenReturn(true);
        when(authenticationMock.getPrincipal()).thenReturn(userDetailsMock);
        when(userDetailsMock.getUsername()).thenReturn("testuser@example.com");

        when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        SecurityContextHolder.setContext(securityContextMock);

        byte[] imageBytes = { 0x01, 0x02, 0x03 };
        MockMultipartFile image = new MockMultipartFile("image.jpg", "image.jpg", "image/jpeg", imageBytes);

        ProductAddBindingModel productAddBindingModel = new ProductAddBindingModel();
        productAddBindingModel.setName("Test Product");
        productAddBindingModel.setImage(image);

        User user = new User();
        when(userRepositoryMock.findByEmail("testuser@example.com")).thenReturn(Optional.of(user));
        when(productRepositoryMock.findByName("Test Product")).thenReturn(null);

        boolean result = productService.addProduct(productAddBindingModel);

        assertTrue(result, "Expected result to be true");

        verify(productRepositoryMock, times(1)).findByName("Test Product");
        verify(userRepositoryMock, times(1)).findByEmail("testuser@example.com");
        verify(productRepositoryMock, times(1)).save(any(Product.class));
    }

    @Test
    void addProduct_WithAuthenticatedUserAndExistingProductName_ShouldNotAddProduct() {
        Authentication authenticationMock = mock(Authentication.class);
        UserDetails userDetailsMock = mock(UserDetails.class);
        when(authenticationMock.isAuthenticated()).thenReturn(true);
        when(authenticationMock.getPrincipal()).thenReturn(userDetailsMock);
        when(userDetailsMock.getUsername()).thenReturn("testuser@example.com");

        SecurityContextHolder.getContext().setAuthentication(authenticationMock);

        ProductAddBindingModel productAddBindingModel = new ProductAddBindingModel();
        productAddBindingModel.setName("Existing Product");
        productAddBindingModel.setImage(new MockMultipartFile("image.jpg", new byte[0]));

        User user = new User();

        when(userRepositoryMock.findByEmail("testuser@example.com")).thenReturn(Optional.of(user));
        when(productRepositoryMock.findByName("Existing Product")).thenReturn(new Product());

        boolean result = productService.addProduct(productAddBindingModel);

        assertFalse(result);
        verify(productRepositoryMock, never()).save(any(Product.class));
    }

    @Test
    void addProduct_WithUnauthenticatedUser_ShouldNotAddProduct() {
        Authentication authenticationMock = mock(Authentication.class);
        when(authenticationMock.isAuthenticated()).thenReturn(false);

        SecurityContextHolder.getContext().setAuthentication(authenticationMock);

        ProductAddBindingModel productAddBindingModel = new ProductAddBindingModel();
        productAddBindingModel.setName("Test Product");
        productAddBindingModel.setImage(new MockMultipartFile("image.jpg", new byte[0]));

        boolean result = productService.addProduct(productAddBindingModel);

        assertFalse(result);
        verify(productRepositoryMock, never()).save(any(Product.class));
    }

    @Test
    void getCategoriesViewData_ShouldReturnAllProducts_WhenFilterTypeIsAll() {
        String filterType = "all";
        List<Product> mockProducts = Arrays.asList(
                new Product("Flower1", CategoryEnum.INDIVIDUAL_FLOWER),
                new Product("Flower2", CategoryEnum.INDIVIDUAL_FLOWER),
                new Product("Bouquet1", CategoryEnum.BOUQUET)
        );
        when(productRepositoryMock.findAll()).thenReturn(mockProducts);

        List<ProductDTO> result = productService.getCategoriesViewData(filterType);

        assertEquals(mockProducts.size(), result.size());
        verify(productRepositoryMock, times(1)).findAll();
    }

    @Test
    void getCategoriesViewData_ShouldReturnFilteredProducts_WhenFilterTypeIsSpecific() {
        String filterType = "INDIVIDUAL_FLOWER";
        List<Product> mockProducts = Arrays.asList(
                new Product("Flower1", CategoryEnum.INDIVIDUAL_FLOWER),
                new Product("Flower2", CategoryEnum.INDIVIDUAL_FLOWER),
                new Product("Bouquet1", CategoryEnum.BOUQUET)
        );
        when(productRepositoryMock.findAll()).thenReturn(mockProducts);

        List<ProductDTO> result = productService.getCategoriesViewData(filterType);

        assertEquals(2, result.size());
        for (ProductDTO productDTO : result) {
            assertEquals(CategoryEnum.INDIVIDUAL_FLOWER.toString(), productDTO.getCategory().toString());
        }
        verify(productRepositoryMock, times(1)).findAll();
    }

    @Test
    void getProductImageById_ShouldReturnImage_WhenProductExistsWithImage() {
        UUID productId = UUID.randomUUID();
        byte[] mockImage = {0x01, 0x02, 0x03};
        Product mockProduct = new Product("TestProduct", CategoryEnum.INDIVIDUAL_FLOWER);
        mockProduct.setId(productId);
        mockProduct.setImage(mockImage);
        when(productRepositoryMock.findById(productId)).thenReturn(Optional.of(mockProduct));

        byte[] result = productService.getProductImageById(productId);

        assertArrayEquals(mockImage, result);
        verify(productRepositoryMock, times(1)).findById(productId);
    }

    @Test
    void getProductImageById_ShouldReturnEmptyArray_WhenProductDoesNotExistOrHasNoImage() {
        UUID productId = UUID.randomUUID();
        when(productRepositoryMock.findById(productId)).thenReturn(Optional.empty());

        byte[] result = productService.getProductImageById(productId);

        assertArrayEquals(new byte[0], result);
        verify(productRepositoryMock, times(1)).findById(productId);
    }

    @Test
    void removeProduct_ShouldRemoveProduct_WhenProductIdExists() {
        UUID productId = UUID.randomUUID();
        doNothing().when(productRepositoryMock).deleteById(productId);

        productService.removeProduct(productId);

        verify(productRepositoryMock, times(1)).deleteById(productId);
    }

    @Test
    void getMostBought_ShouldReturnMostBoughtProducts() {
        List<Product> mockProducts = Arrays.asList(
                new Product("Flower1", CategoryEnum.INDIVIDUAL_FLOWER),
                new Product("Flower2", CategoryEnum.INDIVIDUAL_FLOWER),
                new Product("Bouquet1", CategoryEnum.BOUQUET)
        );
        when(productRepositoryMock.findAll(any(PageRequest.class)))
                .thenReturn(new PageImpl<>(mockProducts));

        List<ProductDTO> result = productService.getMostBought();

        assertEquals(3, result.size());
        verify(productRepositoryMock, times(1)).findAll(any(PageRequest.class));
    }
}
