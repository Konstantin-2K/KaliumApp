package com.Kalium.controller;

import com.Kalium.model.contactMessageEntities.ContactMessageBindingModel;
import com.Kalium.model.contactMessageEntities.ContactMessageSubjectDTO;
import com.Kalium.service.ContactMessageService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ContactsController {

    private final ContactMessageService contactMessageService;

    public ContactsController(ContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    @GetMapping("/contacts")
    public ModelAndView contacts(@ModelAttribute("contactMessageBindingModel") ContactMessageBindingModel contactMessageBindingModel) {
        return new ModelAndView("contacts");
    }

    @PostMapping("/contacts")
    public ModelAndView contacts(@ModelAttribute("contactMessageBindingModel") @Valid ContactMessageBindingModel contactMessageBindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("contacts");
        }

        ModelAndView modelAndView = new ModelAndView("contacts");

        boolean isAdded = contactMessageService.addMessage(contactMessageBindingModel);

        if (isAdded) {
            modelAndView.setViewName("redirect:/contacts");
            modelAndView.addObject("successMessage", "Message was sent successfully");
        }

        return modelAndView;
    }

    @GetMapping("/contacts/messages")
    public ModelAndView contactMessage() {

        ContactMessageSubjectDTO messageData = contactMessageService.getMessageViewData();

        return new ModelAndView("messages", "messages", messageData);
    }

}
