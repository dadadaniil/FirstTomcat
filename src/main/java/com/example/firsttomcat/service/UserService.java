package com.example.firsttomcat.service;

import com.example.firsttomcat.model.User;
import jakarta.mail.MessagingException;

import java.util.Random;

public interface UserService {
    User authenticate(String email, String password);

    boolean userExists(String email);

    void register(String username, String password, String email) throws MessagingException;

    default String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    void changeUsername(String email, String newUsername);

    void createPost(String email, String postContent);

    void deleteAccount(String email);
}
