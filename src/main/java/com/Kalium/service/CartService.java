package com.Kalium.service;

import com.Kalium.model.productEntities.Product;
import com.Kalium.model.productEntities.ProductDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CartService {
    Product getProductById(UUID productId);

    boolean addToCart(UUID productId);

    Map<ProductDTO, Integer> getUserCartProducts();

    void updateQuantity(UUID productId, Integer newQuantity);
}
