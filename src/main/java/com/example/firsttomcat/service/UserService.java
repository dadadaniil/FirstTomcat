package com.example.firsttomcat.service;

import com.example.firsttomcat.model.User;

import java.io.InputStream;
import java.util.Random;

public interface UserService {
    User authenticate(String email, String password);

    boolean userExists(String email);

    void register(String username, String password, String email);

    default String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    void uploadAvatar(InputStream inputStream, String email);

    void changeUsername(String email, String newUsername);

    void sendLetter(String email, String postContent);

    void deleteAccount(String email);
}
