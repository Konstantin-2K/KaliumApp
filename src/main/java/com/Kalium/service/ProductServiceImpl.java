package com.Kalium.service;

import com.Kalium.model.Product;
import com.Kalium.model.ProductAddBindingModel;
import com.Kalium.model.User;
import com.Kalium.repo.ProductRepository;
import com.Kalium.repo.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean addProduct(ProductAddBindingModel productAddBindingModel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Product productByName = productRepository.findByName(productAddBindingModel.getName());

            Object principal = authentication.getPrincipal();
            User user = userRepository.findByEmail(((UserDetails) principal).getUsername()).orElse(new User());

            if (productByName != null) {
                return false;
            }

            Product product = map(productAddBindingModel, user);

            productRepository.save(product);

            return true;
        }

        return false;
    }

    private Product map(ProductAddBindingModel productAddBindingModel, User user) {
        return new Product()
                .setName(productAddBindingModel.getName())
                .setDescription(productAddBindingModel.getDescription())
                .setPrice(productAddBindingModel.getPrice())
                .setAddedBy(user)
                .setAddedDate(LocalDate.now())
                .setCategory(productAddBindingModel.getCategory());
    }
}
