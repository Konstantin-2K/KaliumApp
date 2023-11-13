package com.Kalium.service;

import com.Kalium.model.productEntities.Product;
import com.Kalium.model.productEntities.ProductAddBindingModel;
import com.Kalium.model.productEntities.ProductCategoryDTO;
import com.Kalium.model.productEntities.ProductDTO;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    boolean addProduct(ProductAddBindingModel productAddBindingModel);

    List<ProductDTO> getCategoriesViewData(String filterType);

    byte[] getProductImageById(UUID productId);

    void removeProduct(UUID productId);

    List<ProductDTO> getMostBought();

}
