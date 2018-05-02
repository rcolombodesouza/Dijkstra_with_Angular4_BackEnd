package com.dijkstra.mail.view;

import static com.dijkstra.mail.useful.factory.Factory.controllerSupplier;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.Document;
import org.json.JSONObject;

import com.dijkstra.mail.controller.user.UserController;


@Path("/register")
public class UserRegister {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public String userRegister(String user) {
        JSONObject resultJSON;
        Document doc = Document.parse(user);
        UserController controller = controllerSupplier.get();
        resultJSON = controller.registerNewUser(doc);
        return resultJSON.toString();

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/validate")
    public String validateUser(String user){

        Document userRetrieved;
        Document doc = Document.parse(user);
        UserController controller = controllerSupplier.get();
        userRetrieved = controller.searchUser(doc);
        return userRetrieved.toJson();

    }

}
