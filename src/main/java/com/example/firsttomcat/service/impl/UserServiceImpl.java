package com.example.firsttomcat.service.impl;

import com.example.firsttomcat.model.User;
import com.example.firsttomcat.model.impl.DatabaseUtilImpl;
import com.example.firsttomcat.service.EmailService;
import com.example.firsttomcat.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import java.io.InputStream;

public class UserServiceImpl implements UserService {
    private final DatabaseUtilImpl databaseUtil = new DatabaseUtilImpl();

    @Override
    public User authenticate(String email, String password) {
        User user = databaseUtil.findUserByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public boolean userExists(String email) {
        User user = databaseUtil.findUserByEmail(email);
        return user != null;
    }


    @Override
    public void register(String username, String password, String email) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(username, hashedPassword, email);
        databaseUtil.saveUser(user.toDocument());

//        String verificationCode = generateVerificationCode();
//        databaseUtil.saveVerificationCode(email, verificationCode);
        EmailService.sendConfirmationEmail(email);
    }

    @Override
    public void uploadAvatar(InputStream inputStream, String email) {
        databaseUtil.deleteFileByEmail(email);
        databaseUtil.uploadFile(inputStream, email);
    }


    @Override
    public void changeUsername(String email, String newUsername) {
        User user = databaseUtil.findUserByEmail(email);
        if (user != null) {
            databaseUtil.updateUsername(email, newUsername);
        }
    }

    @Override
    public void sendLetter(String email, String let) {
        databaseUtil.saveLetterToJoBiden(email, let);
    }

    @Override
    public void deleteAccount(String email) {
        databaseUtil.deleteUserByEmail(email);
    }
}