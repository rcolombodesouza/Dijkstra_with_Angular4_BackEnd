package com.dijkstra.mail.connection;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
	
	public static MongoDatabase createConnection(String host, int port, String databaseName, String user, char[] password) throws Exception{
		
		//MongoCredential credential = MongoCredential.createScramSha1Credential(user, databaseName, password);
		MongoClient mongoConnection = new MongoClient(host, port);
		MongoDatabase database = mongoConnection.getDatabase(databaseName);
		System.out.println("Connect to database successfully");		
		return database;		
	}
}
