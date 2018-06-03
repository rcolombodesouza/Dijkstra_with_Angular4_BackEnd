package com.dijkstra.mail.controller.user;

import static com.dijkstra.mail.useful.constants.Constants.FIRSTNAME;
import static com.dijkstra.mail.useful.constants.Constants.LASTNAME;
import static com.dijkstra.mail.useful.constants.Constants.USERNAME;
import static com.dijkstra.mail.useful.constants.Constants.PASS;

import org.bson.Document;
import org.json.JSONObject;

import com.dijkstra.mail.model.entity.User;
import com.dijkstra.mail.model.repository.UserRepository;

public class UserController {

    private UserRepository repository = new UserRepository();
    
    
    /**
     * Register a new user into MongoDB
     * @param doc
     * @return a JSON object for the new user
     */
    public JSONObject registerNewUser(Document doc){
        User user = new User(doc.get(FIRSTNAME).toString(),
                doc.get(LASTNAME).toString(),
                doc.get(USERNAME).toString(),
                doc.get(PASS).toString());
        String response = repository.registerUser(user);
        return new JSONObject(response);
    }

    
    /**
     * Search an existing user based on doc
     * @param doc
     * @return the existing user
     */
    public Document searchUser(Document doc) {
        User response = repository.searchUser(doc.get(USERNAME).toString(), doc.get(PASS).toString());
        return new Document(FIRSTNAME, response.getFirstName()).append
                (LASTNAME, response.getLastName()).append
                (USERNAME, response.getUserName()).append
                (PASS, response.getPassword());
    }
}