package com.Kalium.service.impl;

import com.Kalium.model.orderEntities.Order;
import com.Kalium.model.orderEntities.OrderAddBindingModel;
import com.Kalium.model.productEntities.Product;
import com.Kalium.model.userEntities.User;
import com.Kalium.repo.OrderRepository;
import com.Kalium.repo.ProductRepository;
import com.Kalium.repo.UserRepository;
import com.Kalium.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    ModelMapper modelMapper = new ModelMapper();
    private final ProductRepository productRepository;

    public OrderServiceImpl(UserRepository userRepository, OrderRepository orderRepository,
                            ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public boolean addOrder(OrderAddBindingModel orderAddBindingModel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            User user = userRepository.findByEmail(((UserDetails) principal).getUsername()).orElse(new User());
            Map<Product, Integer> boughtProducts = user.getShoppingCart();

            for (Product product : boughtProducts.keySet()) {
                Product updatedProduct = product;
                int updatedTimesBought = updatedProduct.getTimesBought() + 1;
                updatedProduct.setTimesBought(updatedTimesBought);
                productRepository.save(updatedProduct);
            }

            Order order = modelMapper.map(orderAddBindingModel, Order.class);
            order.setUser(user);
            orderRepository.save(order);
            user.getShoppingCart().clear();
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
