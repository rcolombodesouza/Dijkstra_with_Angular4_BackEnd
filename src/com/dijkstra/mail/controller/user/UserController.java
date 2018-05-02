package com.dijkstra.mail.controller.user;

import org.bson.Document;
import org.json.JSONObject;

import com.dijkstra.mail.model.entity.User;
import com.dijkstra.mail.model.repository.UserRepository;

public class UserController {

    private UserRepository repository = new UserRepository();
    private static final String USERNAME = "userName";
    private static final String FIRSTNAME = "firstName";
    private static final String LASTNAME = "lastName";
    private static final String PASS = "password";


    public JSONObject registerNewUser(Document doc){
        User user = new User(doc.get(FIRSTNAME).toString(),
                doc.get(LASTNAME).toString(),
                doc.get(USERNAME).toString(),
                doc.get(PASS).toString());
        String response = repository.registerUser(user);
        return new JSONObject(response);
    }

    public Document searchUser(Document doc) {
        User response = repository.searchUser(doc.get(USERNAME).toString(), doc.get(PASS).toString());
        return new Document(FIRSTNAME, response.getFirstName()).append
                (LASTNAME, response.getLastName()).append
                (USERNAME, response.getUserName()).append
                (PASS, response.getPassword());
    }
}