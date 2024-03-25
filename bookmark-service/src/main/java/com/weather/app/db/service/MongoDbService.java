package com.weather.app.db.service;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Service
public class MongoDbService {

	@Value("${spring.data.mongodb.uri}")
	private String mongoConnection;

	@Value("${spring.data.mongodb.database}")
	private String dataBaseName;

	public MongoCollection<Document> getMongoDatabaseCollection(String collectionName) {
		MongoDatabase mongoDatabase = null;
		MongoCollection<Document> mongoCollection = null;
		try {
			MongoClient mongoClient = MongoClients.create(mongoConnection);
			mongoDatabase = mongoClient.getDatabase(dataBaseName);
			mongoCollection = mongoDatabase.getCollection(collectionName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mongoCollection;
	}

}
