package com.dijkstra.mail.connection.dao;

import org.bson.Document;

import com.dijkstra.mail.connection.MongoDBConnection;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import com.mongodb.client.MongoDatabase;

public class UserDAO {
	
	private MongoDatabase database = null;
	//MongoCollection<Document> collection = null;

	public UserDAO() throws Exception{
		this.createConnection();
	}
	
	private MongoDatabase getDatabase() {
		return database;
	}
	private void setDatabase(MongoDatabase database) {
		this.database = database;
	}

	private void createConnection() throws Exception {
		char[] a = new char[3];
		a[0] = '1';
		a[1] = '2';
		a[2] = '3';
		this.setDatabase(MongoDBConnection.createConnection("localhost", 27017, "Dijkstra", "teste", a)); 
		this.createIndex();
	}
	
	private void createIndex() throws Exception {
		this.getDatabase().getCollection("User").createIndex(new BasicDBObject("userName", 1));
	}
	
	public void insertDocument(Document doc) throws Exception{		
		this.getDatabase().getCollection("User").insertOne(doc);		
	}
	
	public Document searchDocument(Document doc) throws Exception {
		FindIterable<Document> search =  this.getDatabase().getCollection("User").find(doc);
		 
		
		if (search != null) {
			 return search.first();
		 }	
		 return null;
	}
}
