package com.dijkstra.mail.connection.userinterface;

import com.dijkstra.mail.connection.entity.User;
import org.json.JSONObject;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, String> {

    JSONObject save(User entity);
    User findOne(String primaryKey);

}
