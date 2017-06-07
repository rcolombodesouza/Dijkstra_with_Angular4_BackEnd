package com.dijkstra.mail.connection.dao;

import com.dijkstra.mail.connection.entity.User;
import com.dijkstra.mail.connection.userinterface.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongoDBDaoImpl  {

    @Autowired
    private UserRepository repository;

    public JSONObject saveUser(User user){
        return repository.save(user);
    }

    public User findUser(String id){
        return repository.findOne(id);
    }

}
