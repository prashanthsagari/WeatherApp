package com.weather.app.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import com.weather.app.config.security.crypto.PasswordEncoderService;
import com.weather.app.config.security.jwt.JWTTokenGenerator;
import com.weather.app.db.service.MongoDbService;
import com.weather.app.exception.InactiveUserException;
import com.weather.app.exception.PasswordNotMatchException;
import com.weather.app.exception.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private MongoDbService mongoDbService;

	@Value("${app.mongodb.collection}")
	private String mongoCollection;

	@Autowired
	JWTTokenGenerator jwtTokenGenerator;

	@Autowired
	PasswordEncoderService passwordEncoderService;

	@Override
	public Document registerUserUpsert(Document userDocument) {
		MongoCollection<Document> collection = mongoDbService.getMongoDatabaseCollection(mongoCollection);

		Bson filterQuery = Filters.or(Filters.eq("username", userDocument.getString("username")),
				Filters.eq("email", userDocument.getString("email")));

		Document insertedDocument = collection.find(filterQuery).first();
		
		String plainPassword = userDocument.getString("password");
		String encodedPassword = passwordEncoderService.encodePassword(plainPassword);
		userDocument.put("password", encodedPassword);

		if (insertedDocument != null) {
//			throw new LoginUserAlreadyAvailable("User or email already available.");
			UpdateOptions options = new UpdateOptions().upsert(false);
			collection.updateOne(filterQuery, new Document("$set", userDocument), options);

		} else {
			userDocument.put("active", "ACTIVE");
			userDocument.put("retries", 0);
			collection.insertOne(userDocument);
		}
		return userDocument;
	}
	
	@Override
	public String updateEmail(String userName, String email) {
		MongoCollection<Document> collection = mongoDbService.getMongoDatabaseCollection(mongoCollection);
		Bson filterQuery = Filters.and(Filters.eq("username", userName));
		Document user = collection.find(filterQuery).first();
		if (user != null) {
			Bson update = Updates.set("email", email);
			UpdateOptions options = new UpdateOptions().upsert(true);
			collection.updateOne(filterQuery, update, options);
			return "Successfully Updated.";
		}
		return "Failed to update";
	}

	@Override
	public Document login(Document userDocument) {
		MongoCollection<Document> collection = mongoDbService.getMongoDatabaseCollection(mongoCollection);
		Bson filterQuery = Filters.or(Filters.eq("username", userDocument.getString("username")),
				Filters.eq("email", userDocument.getString("email")));
		Document user = collection.find(filterQuery).first();
		if (user != null) {
			String password = userDocument.getString("password");
			String status = user.getString("active");

			if (status.equalsIgnoreCase("INACTIVE"))
				throw new InactiveUserException("User is disabled.");

			boolean isValid = passwordEncoderService.matches(password, user.getString("password"));
			int retries = user.getInteger("retries");
			if (!isValid) {
				List<Bson> bsonList = new ArrayList<>();
				if (retries > 3) {
					bsonList.add(Updates.set("active", "INACTIVE"));
				}
				bsonList.add(Updates.set("retries", retries + 1));

				UpdateOptions options = new UpdateOptions().upsert(true);
				collection.updateOne(filterQuery, bsonList, options);

				throw new PasswordNotMatchException("Wrong Password");
			} else if (retries != 0) {
				Bson update = Updates.set("retries", 0);
				UpdateOptions options = new UpdateOptions().upsert(true);
				collection.updateOne(filterQuery, update, options);
			}

		} else {
			throw new ResourceNotFoundException("User not found.");
		}

		return jwtTokenGenerator.generateJWTToken(user);
	}

	@Override
	public String actOrDeactUser(String userName, String status) {
		MongoCollection<Document> collection = mongoDbService.getMongoDatabaseCollection(mongoCollection);

		List<Bson> bsonList = new ArrayList<>();
		bsonList.add(Updates.set("active", status));
		bsonList.add(Updates.set("retries", 0));

		Bson filter = Filters.eq("username", userName);

		Document user = collection.find(filter).first();

		if (user == null) {
			throw new ResourceNotFoundException("User not found.");
		}

		UpdateOptions options = new UpdateOptions().upsert(true);
		collection.updateOne(filter, bsonList, options);

		return status == "ACTIVE" ? "User is active now." : "User is deactivated.";
	}

	@Override
	public boolean deleteUser(String userName) {
		MongoCollection<Document> collection = mongoDbService.getMongoDatabaseCollection(mongoCollection);
		Bson filterQuery = Filters.and(Filters.eq("username", userName));
		Document user = collection.find(filterQuery).first();
		if (user == null) {
			throw new ResourceNotFoundException("User not found.");
		}
		return collection.deleteOne(filterQuery).wasAcknowledged();
	}

	@Override
	public List<Document> getAllUsers() {
		MongoCollection<Document> collection = mongoDbService.getMongoDatabaseCollection(mongoCollection);
		List<Document> listOfUsers = new ArrayList<>();
		for (Document document : collection.find()) {
			listOfUsers.add(document);
		}
		return listOfUsers;
	}

	@Override
	public Document getUser(String userName) {
		MongoCollection<Document> collection = mongoDbService.getMongoDatabaseCollection(mongoCollection);
		Bson filterQuery = Filters.and(Filters.eq("username", userName));
		Document user = collection.find(filterQuery).first();
		if (user == null) {
			throw new ResourceNotFoundException("User not found.");
		}
		return collection.find(filterQuery).first();
	}

}
