package com.Kalium.service.impl;

import com.Kalium.model.productEntities.Product;
import com.Kalium.model.productEntities.ProductDTO;
import com.Kalium.model.userEntities.User;
import com.Kalium.repo.ProductRepository;
import com.Kalium.repo.UserRepository;
import com.Kalium.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartServiceImpl implements CartService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public boolean addToCart(UUID productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            User user = userRepository.findByEmail(((UserDetails) principal).getUsername()).orElse(new User());
            Product product = getProductById(productId);

            if (!user.getShoppingCart().containsKey(product)) {
                user.getShoppingCart().put(product, 1);
            } else {
                int previousQuantity = user.getShoppingCart().get(product);
                user.getShoppingCart().put(product, previousQuantity + 1);
            }

            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public Map<ProductDTO, Integer> getUserCartProducts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<ProductDTO, Integer> cartProducts = new HashMap<>();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            User user = userRepository.findByEmail(((UserDetails) principal).getUsername()).orElse(new User());
            Map<Product, Integer> rawCartProducts = user.getShoppingCart();

            for (Product product : rawCartProducts.keySet()) {
                ProductDTO currentProductDTO = ProductDTO.createFromProduct(product);
                Integer currentProductQuantity = rawCartProducts.get(product);
                cartProducts.put(currentProductDTO, currentProductQuantity);
            }
        }
        return cartProducts;
    }

    @Override
    public void removeFromCart(UUID productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            User user = userRepository.findByEmail(((UserDetails) principal).getUsername()).orElse(new User());
            Optional<Product> optionalProduct = productRepository.findById(productId);

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                user.getShoppingCart().remove(product);
                userRepository.save(user);
            }
        }
    }

    @Override
    public Product getProductById(UUID productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            return productOptional.get();
        }
        return new Product();
    }
}
