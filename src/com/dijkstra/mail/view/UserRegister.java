package com.dijkstra.mail.view;


import com.dijkstra.mail.controller.user.UserController;
import com.dijkstra.mail.useful.factory.Factory;
import org.bson.Document;
import org.json.JSONObject;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/register")
public class UserRegister {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/create")
	public String userRegister(String user) {
		JSONObject resultJSON;
		Document doc = Document.parse(user);
		UserController controller = Factory.CONTROLLERSUPPLIER.get();
		resultJSON = controller.registerNewUser(doc);
		return resultJSON.toString();

	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/validate")
	public String validateUser(String user){	
		
		Document userRetrieved;
		Document doc = Document.parse(user);
		UserController controller = Factory.CONTROLLERSUPPLIER.get();
		userRetrieved = controller.searchUser(doc);
		return userRetrieved.toJson();

	}
	
}
