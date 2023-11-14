package com.Kalium.controller;

import com.Kalium.event.UserRegisterEvent;
import com.Kalium.model.userEntities.UserDTO;
import com.Kalium.model.userEntities.UserLoginBindingModel;
import com.Kalium.model.userEntities.UserRegisterBindingModel;
import com.Kalium.service.UserService;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
public class UserController {

    private final UserService userService;

    private final ApplicationEventPublisher eventPublisher;

    public UserController(UserService userService, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;

        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/login")
    public ModelAndView login(@ModelAttribute("userLoginBindingModel") UserLoginBindingModel userLoginBindingModel) {
        return new ModelAndView("login");
    }

    @GetMapping("/register")
    public ModelAndView register(@ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel) {
        return new ModelAndView("register");
    }


    @PostMapping("/login-error")
    public String onFailure(
            @ModelAttribute("email") String email,
            Model model) {

        model.addAttribute("email", email);
        model.addAttribute("bad_credentials", "true");

        return "login";
    }

    @PostMapping("/register")
    public ModelAndView register(@RequestParam String email, @ModelAttribute("userRegisterBindingModel") @Valid UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("register");
        }

        boolean isRegistered = userService.registerUser(userRegisterBindingModel);

        if (isRegistered) {
            eventPublisher.publishEvent(new UserRegisterEvent(this, email));
        }

        String view = isRegistered ? "redirect:/login" : "register";

        return new ModelAndView(view);
    }

    @GetMapping("/users/manageUsers")
    public ModelAndView manageUsers() {

        List<UserDTO> userData = userService.getUserData();

        return new ModelAndView("manage-users", "userData", userData);
    }

    @PostMapping("/users/manageUsers/deleteUser/{userId}")
    public ModelAndView deleteUser(@PathVariable UUID userId) {

        userService.deleteUser(userId);

        return new ModelAndView("redirect:/users/manageUsers");
    }

    @PostMapping("/users/manageUsers/changeRole/{userId}")
    public ModelAndView changeRole(@PathVariable UUID userId) {

        userService.changeRole(userId);

        return new ModelAndView("redirect:/users/manageUsers");
    }
}