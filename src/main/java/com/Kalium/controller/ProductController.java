package com.Kalium.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {

    @GetMapping("/products/categories")
    public ModelAndView home() {
        return new ModelAndView("categories");
    }

    @GetMapping("/products/individual-flowers")
    public ModelAndView individualFlowers() {
        return new ModelAndView("individual-flowers");
    }

    @GetMapping("/products/add")
    public ModelAndView products() {
        return new ModelAndView("add-product");
    }
}
