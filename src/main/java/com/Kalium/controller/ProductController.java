package com.Kalium.controller;

import com.Kalium.model.ProductAddBindingModel;
import com.Kalium.model.ProductCategoryDTO;
import com.Kalium.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.UUID;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/categories")
    public ModelAndView home() {
        return new ModelAndView("categories");
    }

    @GetMapping("/products/individual-flowers")
    public ModelAndView individualFlowers() {

        ProductCategoryDTO categoriesData = productService.getCategoriesViewData();

        return new ModelAndView("individual-flowers", "products", categoriesData);
    }

    @GetMapping("/products/bouquets")
    public ModelAndView bouquets() {

        ProductCategoryDTO categoriesData = productService.getCategoriesViewData();

        return new ModelAndView("bouquets", "products", categoriesData);
    }

    @GetMapping("/products/presents")
    public ModelAndView presents() {

        ProductCategoryDTO categoriesData = productService.getCategoriesViewData();

        return new ModelAndView("presents", "products", categoriesData);
    }

    @GetMapping("/products/special-offers")
    public ModelAndView specialOffers() {

        ProductCategoryDTO categoriesData = productService.getCategoriesViewData();

        return new ModelAndView("special-offers", "products", categoriesData);
    }

    @GetMapping("/products/add")
    public ModelAndView products(@ModelAttribute("productAddBindingModel") ProductAddBindingModel productAddBindingModel) {
        return new ModelAndView("add-product");
    }

    @PostMapping("/products/add")
    public ModelAndView addProduct(@ModelAttribute("productAddBindingModel") @Valid ProductAddBindingModel productAddBindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("add-product");
        }

        boolean isAdded = productService.addProduct(productAddBindingModel);

        if (!isAdded) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("hasAddError", true);
            return modelAndView;
        }

        return new ModelAndView("redirect:/products/add");
    }

    @GetMapping("/products/images/{productId}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable UUID productId) {
        byte[] imageData = productService.getProductImageById(productId);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
    }
}
