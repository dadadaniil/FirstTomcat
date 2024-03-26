package com.example.firsttomcat.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class EmailUtility {

    public static void sendConfirmationEmail(String to, String text) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("stconfmail@gmail.com");
        mailSender.setPassword("&(sf^2hj*jd.-1");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Platform verification code");
        message.setText(text);

        mailSender.send(message);
    }
}
