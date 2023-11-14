package com.Kalium.event;

import com.Kalium.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterEventListener {

    private final EmailService emailService;

    public UserRegisterEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    @Async
    public void handleUserRegisteredEvent(UserRegisterEvent event) {
        String userEmail = event.getTo();

        emailService.sendWelcomeEmail(userEmail);
    }
}
