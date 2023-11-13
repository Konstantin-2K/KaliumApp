package com.Kalium.controller;

import com.Kalium.model.productEntities.ProductDTO;
import com.Kalium.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {

    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public ModelAndView index() {

        List<ProductDTO> selectedProducts = productService.getMostBought();

        return new ModelAndView("index", "products", selectedProducts);
    }

}
