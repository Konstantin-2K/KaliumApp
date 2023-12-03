package com.Kalium.controller;

import com.Kalium.model.productEntities.Product;
import com.Kalium.model.productEntities.ProductAddBindingModel;
import com.Kalium.model.productEntities.ProductCategoryDTO;
import com.Kalium.model.productEntities.ProductDTO;
import com.Kalium.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ModelAndView productsPage() {

        List<ProductDTO> categoriesData = productService.getCategoriesViewData("all");

        return new ModelAndView("products", "products", categoriesData);
    }

    @GetMapping("/products/individualFlowers")
    public ModelAndView individualFlowers() {

        List<ProductDTO> categoriesData = productService.getCategoriesViewData("INDIVIDUAL_FLOWER");

        return new ModelAndView("products", "products", categoriesData);
    }

    @GetMapping("/products/bouquets")
    public ModelAndView bouquets() {

        List<ProductDTO> categoriesData = productService.getCategoriesViewData("BOUQUET");

        return new ModelAndView("products", "products", categoriesData);
    }

    @GetMapping("/products/presents")
    public ModelAndView presents() {

        List<ProductDTO> categoriesData = productService.getCategoriesViewData("PRESENT");

        return new ModelAndView("products", "products", categoriesData);
    }

    @GetMapping("/products/specialOffers")
    public ModelAndView specialOffers() {

        List<ProductDTO> categoriesData = productService.getCategoriesViewData("SPECIAL_OFFER");

        return new ModelAndView("products", "products", categoriesData);
    }

    @GetMapping("/products/add")
    public ModelAndView addProduct(@ModelAttribute("productAddBindingModel") ProductAddBindingModel productAddBindingModel) {
        return new ModelAndView("add-product");
    }

    @PostMapping("/products/add")
    public ModelAndView addProduct(@ModelAttribute("productAddBindingModel") @Valid ProductAddBindingModel productAddBindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("add-product");
        }

        boolean isAdded = productService.addProduct(productAddBindingModel);

        if (!isAdded) {
            ModelAndView modelAndView = new ModelAndView("add-product");
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

    @GetMapping("/products/manageProducts")
    public ModelAndView manageProducts() {

        List<ProductDTO> productData = productService.getCategoriesViewData("all");

        return new ModelAndView("manage-products", "products", productData);
    }

    @PostMapping("/products/remove/{productId}")
    public ModelAndView addProduct(@PathVariable UUID productId) {

        productService.removeProduct(productId);

        return new ModelAndView("redirect:/products/manageProducts");
    }
}
