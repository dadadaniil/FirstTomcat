package com.example.firsttomcat.servlet.model;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {
    private static final Logger logger = LogManager.getLogger(DatabaseUtil.class);
    private static final MongoClient connection = MongoDBConnection.getInstance();

    public void saveUser(String username, String password, String email) {
        MongoDatabase database = connection.getDatabase("data_base");
        MongoCollection<Document> collection = database.getCollection("users");
        Document user = new Document("username", username).append("password", password).append("email", email);
        collection.insertOne(user);
    }

    public FindIterable<Document> getUsers() {
        MongoDatabase database = connection.getDatabase("data_base");
        MongoCollection<Document> collection = database.getCollection("users");
        for (Document doc : collection.find()) {
            System.out.println(doc.toJson());
        }
        return collection.find();
    }

    public List<User> getUsersList() {
        List<User> users = new ArrayList<>();
        for (Document doc : this.getUsers()) {
            users.add(new User(doc.getString("username"), doc.getString("password"), doc.getString("email")));
        }
        if (users.isEmpty()) {
            logger.error("No users found");
        }
        return users;
    }


    public void saveUser(Document document) {
        MongoDatabase database = connection.getDatabase("data_base");
        MongoCollection<Document> collection = database.getCollection("users");

        collection.insertOne(document);
    }

    public User findUserByEmail(String email) {
        MongoDatabase database = connection.getDatabase("data_base");
        MongoCollection<Document> collection = database.getCollection("users");
        Document query = new Document("email", email);
        Document user = collection.find(query).first();

        if (user != null) {
            return new User(user.getString("username"), user.getString("password"), user.getString("email"));
        } else {
            logger.error("User with email " + email + " not found");
            return null;
        }
    }

    public void deleteUserByEmail(String email) {
        MongoDatabase database = connection.getDatabase("data_base");
        MongoCollection<Document> collection = database.getCollection("users");
        Document query = new Document("email", email);
        collection.deleteOne(query);
    }


    public void saveVerificationCode(String email, String verificationCode) {
        MongoDatabase database = connection.getDatabase("data_base");
        MongoCollection<Document> collection = database.getCollection("verification_codes");
        Document code = new Document("email", email).append("code", verificationCode);
        collection.insertOne(code);
    }

    public boolean checkVerificationCode(String email, String verificationCode) {
        MongoDatabase database = connection.getDatabase("data_base");
        MongoCollection<Document> collection = database.getCollection("verification_codes");
        Document query = new Document("email", email).append("code", verificationCode);
        Document code = collection.find(query).first();
        return code != null;
    }

    public void updateUsername(String email, String newUsername) {
        MongoDatabase database = connection.getDatabase("data_base");
        MongoCollection<Document> collection = database.getCollection("users");

        // Create a query document to find the user by their email
        Document query = new Document("email", email);

        // Create an update document to set the new username
        Document update = new Document("$set", new Document("username", newUsername));

        // Update the user's username
        collection.updateOne(query, update);
    }
}