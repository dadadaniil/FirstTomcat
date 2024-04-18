package com.example.firsttomcat.service;

import org.springframework.mail.SimpleMailMessage;

public class EmailService {

    public static void sendConfirmationEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Registration confirmation");
        message.setText("your account has been successfully registered.");

        EmailConfig.sendEmail(message);
    }
}