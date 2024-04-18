package com.example.firsttomcat.model;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MongoDBConnection {
    private static MongoClient mongoClient;

private static final Logger logger = LogManager.getLogger(MongoDBConnection.class);

    private MongoDBConnection() {
        // ???
    }

    public static MongoClient getInstance() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            logger.info("MongoDB connection established");
        }
        return mongoClient;
    }
}