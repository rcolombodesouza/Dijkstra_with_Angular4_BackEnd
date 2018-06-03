package com.dijkstra.mail.model.repository;

import static com.dijkstra.mail.useful.constants.Constants.USERNAME;
import static com.dijkstra.mail.useful.constants.Constants.PASS;
import static java.util.logging.Level.INFO;

import java.util.logging.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.dijkstra.mail.model.entity.User;
import com.dijkstra.mail.model.mongoconfig.SpringMongoConfig;



@Repository
public class UserRepository {

    private ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
    private MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
    private static final Logger LOGGER = Logger.getLogger(UserRepository.class.getName());

    public User searchUser(String userName, String password){
        Query query = new Query();
        query.addCriteria(Criteria.where(USERNAME).is(userName).and(PASS).is(password));
        User result = mongoOperation.findOne(query, User.class);
        LOGGER.log(INFO, "User {} found ", result);
        return result;
    }

    public String registerUser(User user){

        User userCheck = this.searchUser(user.getUserName(), user.getPassword());

        if(userCheck != null){
        	LOGGER.log(INFO, "User {} already exists", userCheck);
            return "NOK";
        }
        mongoOperation.save(user);
        LOGGER.log(INFO, "User {} registered with success", user);
        return "OK";
    }
}
