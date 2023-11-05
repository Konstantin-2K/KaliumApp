package com.Kalium.controller;

import com.Kalium.model.ProductAddBindingModel;
import com.Kalium.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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
        return new ModelAndView("individual-flowers");
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
}
