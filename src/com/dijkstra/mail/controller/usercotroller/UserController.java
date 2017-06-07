package com.dijkstra.mail.controller.usercotroller;

import com.dijkstra.mail.connection.dao.MongoDBDaoImpl;
import com.dijkstra.mail.connection.entity.User;
import org.bson.Document;
import org.json.JSONObject;

public class UserController {

	private MongoDBDaoImpl daoImpl = new MongoDBDaoImpl();
	
	public UserController(){}

	public JSONObject checkUserInformation(Document doc){
		User user = new User(doc.get("firstName").toString(),
							 doc.get("lastName").toString(),
							 doc.get("userName").toString(),
							 doc.get("password").toString());
		return daoImpl.saveUser(user);
	}

	public Document validateUser(Document doc){
		User user = daoImpl.findUser(doc.get("userName").toString());
		Document userDoc = new Document();
		return userDoc.append("", user);
	}

}