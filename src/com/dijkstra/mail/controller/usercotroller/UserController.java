package com.dijkstra.mail.controller.usercotroller;

import org.bson.Document;
import org.json.JSONObject;
import com.dijkstra.mail.connection.dao.UserDAO;
import com.dijkstra.mail.factory.Factory;

public class UserController {
	
	private UserDAO connection;
	
	public UserController(){
		try {
			connection = new UserDAO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public JSONObject checkUserInformation(Document doc){
		JSONObject result = Factory.JSONSUPPLIER.get();
		try {			
			connection.insertDocument(doc);			
			return result.append("Result", "OK");
		} catch (Exception e) {
			return result.append("Result", "NOK");
		}
	}
	
	
	public Document validateUser(Document doc){
		Document user = Factory.DOCSUPPLIER.get();
		try {
			user = connection.searchDocument(doc);
			
			if(user == null){
				user = Factory.DOCSUPPLIER.get();
				user.append("Result", "NOK");				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
}
