package com.example.firsttomcat.model;

import com.mongodb.client.FindIterable;
import org.bson.Document;

import java.util.List;

public interface DatabaseUtil {
    void saveUser(String username, String password, String email);

    FindIterable<Document> receiveUsers();

    List<User> receiveUsersList();

    void saveUser(Document document);

    User findUserByEmail(String email);

    void deleteUserByEmail(String email);

    void saveVerificationCode(String email, String verificationCode);

    boolean checkVerificationCode(String email, String verificationCode);

    void updateUsername(String email, String newUsername);

    void saveLetterToJoBiden(String email, String letterText);
}
