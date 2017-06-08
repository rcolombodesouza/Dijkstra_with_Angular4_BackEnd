package com.dijkstra.mail.controller.user;

import com.dijkstra.mail.model.entity.User;
import com.dijkstra.mail.model.repository.UserRepository;
import org.bson.Document;
import org.json.JSONObject;

public class UserController {

    private UserRepository repository = new UserRepository();

	public UserController(){}

	public JSONObject registerNewUser(Document doc){
        User user = new User(doc.get("firstName").toString(),
                             doc.get("lastName").toString(),
                             doc.get("userName").toString(),
                             doc.get("password").toString());
        String response = repository.registerUser(user);
        return new JSONObject(response);
    }

    public Document searchUser(Document doc) {
        User response = repository.searchUser(doc.get("userName").toString(), doc.get("password").toString());
        return new Document("firstName", response.getFirstName()).append
                           ("lastName", response.getLastName()).append
                           ("userName", response.getUserName()).append
                           ("password", response.getPassword());
    }


}