package com.Kalium.service;

import com.Kalium.model.productEntities.ProductAddBindingModel;
import com.Kalium.model.productEntities.ProductCategoryDTO;

import java.util.UUID;

public interface ProductService {
    boolean addProduct(ProductAddBindingModel productAddBindingModel);

    ProductCategoryDTO getCategoriesViewData();

    byte[] getProductImageById(UUID productId);
}
