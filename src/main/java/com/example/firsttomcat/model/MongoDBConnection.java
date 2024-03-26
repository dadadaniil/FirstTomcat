package com.example.firsttomcat.model;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoDBConnection {
    private static MongoClient mongoClient;

    private MongoDBConnection() {
        // ???
    }

    public static MongoClient getInstance() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            System.out.println("Connection to MongoDB established");
        }
        return mongoClient;
    }
}