package com.dijkstra.mail.restservice;


import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.bson.Document;
import org.json.JSONObject;

import com.dijkstra.mail.controller.usercotroller.UserController;
import com.dijkstra.mail.factory.Factory;


@Path("/register")
public class UserRegister {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/create")
	public String userRegister(String user) {
		JSONObject resultJSON;
		Document doc = Document.parse(user);
		UserController controller = Factory.CONTROLLERSUPPLIER.get();
		resultJSON = controller.checkUserInformation(doc);
		return resultJSON.toString();		
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/validate")
	public String validateUser(String user){	
		
		Document userRetrieved;
		Document doc = Document.parse(user);
		UserController controller = Factory.CONTROLLERSUPPLIER.get();
		userRetrieved = controller.validateUser(doc);		
		return userRetrieved.toJson();
	}
	
}
