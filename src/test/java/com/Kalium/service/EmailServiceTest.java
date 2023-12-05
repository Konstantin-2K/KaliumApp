package com.Kalium.service;

import com.Kalium.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.verify;

class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendWelcomeEmail_ShouldSendEmailSuccessfully() {
        String toEmail = "test@example.com";
        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setTo(toEmail);
        expectedMessage.setSubject("Welcome to Kalium!");
        expectedMessage.setText("Thank you for registering! Welcome to our flower shop.");

        emailService.sendWelcomeEmail(toEmail);

        verify(javaMailSender).send(expectedMessage);
    }
}
