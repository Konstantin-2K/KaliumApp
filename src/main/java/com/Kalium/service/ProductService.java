package com.Kalium.service;

import com.Kalium.model.Product;
import com.Kalium.model.ProductAddBindingModel;
import com.Kalium.model.ProductCategoryDTO;

import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    boolean addProduct(ProductAddBindingModel productAddBindingModel);

    ProductCategoryDTO getCategoriesViewData();

    byte[] getProductImageById(UUID productId);
}
