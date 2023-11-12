package com.Kalium.controller;

import com.Kalium.model.orderEntities.OrderAddBindingModel;
import com.Kalium.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/shoppingCart/checkout")
    public ModelAndView checkout(@ModelAttribute("orderAddBindingModel") OrderAddBindingModel orderAddBindingModel) {
        return new ModelAndView("checkout");
    }

    @GetMapping("/shoppingCart/orderSent")
    public ModelAndView checkout() {
        return new ModelAndView("successful-order");
    }

    @PostMapping("/shoppingCart/checkout")
    public ModelAndView checkout(@ModelAttribute("orderAddBindingModel") @Valid OrderAddBindingModel orderAddBindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("checkout");
        }

        boolean isAdded = orderService.addOrder(orderAddBindingModel);

        if (!isAdded) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("hasAddError", true);
            return modelAndView;
        }

        return new ModelAndView("redirect:/shoppingCart/orderSent");
    }
}
