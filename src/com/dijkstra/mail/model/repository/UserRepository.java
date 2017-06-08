package com.dijkstra.mail.model.repository;


import com.dijkstra.mail.model.entity.User;
import com.dijkstra.mail.model.mongoconfig.SpringMongoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;



@Repository
public class UserRepository {

    ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
    MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
    static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    public User searchUser(String userName, String password){
        Query query = new Query();
        query.addCriteria(Criteria.where("userName").is(userName).and("password").is(password));
        User result = mongoOperation.findOne(query, User.class);
        logger.info("User {} found ", result);
        return result;
    }

    public String registerUser(User user){

        User userCheck = this.searchUser(user.getUserName(), user.getPassword());

        if(userCheck != null){
            logger.info("User {} already exists", userCheck);
            return "NOK";
        } else {
            mongoOperation.save(user);
            logger.info("User {} registered with success", user);
        }
        return "OK";
    }

}
