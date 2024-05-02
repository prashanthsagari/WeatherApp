package com.weather.app.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.weather.app.db.service.MongoDbService;
import com.weather.app.resp.pojo.Favorite;
import com.weather.app.resp.pojo.Favorites;
import com.weather.app.service.BookmarkService;

import io.jsonwebtoken.io.IOException;

@Service
public class BookmarkServiceImpl implements BookmarkService {

	@Autowired
	private MongoDbService mongoDbService;

	@Value("${app.mongodb.collection}")
	private String mongoCollection;

	@Override
	public Document upsertBookmark(String username, Document bookmarkDetails) {
		MongoCollection<Document> collection = mongoDbService.getMongoDatabaseCollection(mongoCollection);
		Bson filterQuery = Filters.eq("username", username);

		Document fetchedData = collection.find(filterQuery).first();

		if (fetchedData != null) {
			List<Document> docs = (List<Document>) fetchedData.get("favorites");
			docs.add(bookmarkDetails);
			UpdateOptions options = new UpdateOptions().upsert(false);
			collection.updateOne(filterQuery, new Document("$set", fetchedData), options);

		} else {
			Document doc = new Document();
			doc.put("username", username);
			doc.put("favorites", List.of(bookmarkDetails));
			collection.insertOne(doc);
			return doc;
		}
		return fetchedData;

	}

	@Override
	public Document bookmarks(String username) {
		MongoCollection<Document> collection = mongoDbService.getMongoDatabaseCollection(mongoCollection);
		Bson filterQuery = Filters.eq("username", username);
		return collection.find(filterQuery).first();
	}

	public List<Document> getAllBookmarks() {
		MongoCollection<Document> collection = mongoDbService.getMongoDatabaseCollection(mongoCollection);
		List<Document> listOfBookmarks = new ArrayList<>();
		for (Document document : collection.find()) {
			listOfBookmarks.add(document);
		}
		return listOfBookmarks;
	}

	@Override
	public Document deleteBookMark(String username, String id) {
		MongoCollection<Document> collection = mongoDbService.getMongoDatabaseCollection(mongoCollection);
		Bson filterQuery = Filters.eq("username", username);
		Document user = collection.find(filterQuery).first();

		if (user != null) {
			Document update = new Document("$pull", new Document("favorites", new Document("id", id)));
			collection.updateOne(filterQuery, update);
			return collection.find(filterQuery).first();
		}
		return null;
	}

	public static void main(String[] args) {
		BookmarkServiceImpl impl = new BookmarkServiceImpl();
		impl.abc();
	}

	public void abc() {
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		MongoDatabase mongoDatabase = mongoClient.getDatabase("favorites_db");
		MongoCollection<Document> collection = mongoDatabase.getCollection("bookmarks");

		Bson filterQuery = Filters.eq("username", "mno");
		Document user = collection.find(filterQuery).first();

		Document update = new Document("$pull",
				new Document("favorites", new Document("id", "2e61210a-4380-419a-b427-51a25c5aaf67")));
		collection.updateOne(filterQuery, update);

		System.out.println("Document updated successfully.");

	}

}
