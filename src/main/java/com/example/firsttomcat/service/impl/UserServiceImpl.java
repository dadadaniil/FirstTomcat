package com.example.firsttomcat.service.impl;

import com.example.firsttomcat.model.User;
import com.example.firsttomcat.model.impl.DatabaseUtilImpl;
import com.example.firsttomcat.service.UserService;
import jakarta.mail.MessagingException;
import org.mindrot.jbcrypt.BCrypt;

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
    public void register(String username, String password, String email) throws MessagingException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(username, hashedPassword, email);
        databaseUtil.saveUser(user.toDocument());

        String verificationCode = generateVerificationCode();
        databaseUtil.saveVerificationCode(email, verificationCode);
//        EmailUtility.sendConfirmationEmail(email, verificationCode);
    }


    @Override
    public void changeUsername(String email, String newUsername) {
        User user = databaseUtil.findUserByEmail(email);
        if (user != null) {
            databaseUtil.updateUsername(email, newUsername);
        }
    }

    @Override
    public void createPost(String email, String postContent) {
    }

    @Override
    public void deleteAccount(String email) {
        databaseUtil.deleteUserByEmail(email);
    }
}