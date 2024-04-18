package com.example.firsttomcat.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class EmailConfig {

    private static final JavaMailSenderImpl mailSender;

    static {
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost(EmailConfigConstants.HOST);
        mailSender.setPort(EmailConfigConstants.PORT);
        mailSender.setUsername(EmailConfigConstants.USERNAME);
        mailSender.setPassword(EmailConfigConstants.PASSWORD);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
    }

    public static void sendEmail(SimpleMailMessage message) {
        mailSender.send(message);
    }
}