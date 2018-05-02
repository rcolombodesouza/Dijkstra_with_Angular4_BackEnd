package com.dijkstra.mail.view;

import static com.dijkstra.mail.useful.factory.Factory.jsonSupplier;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.dijkstra.mail.controller.SearchService;


@Path("/dijkstra")
public class RestService {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String dijkstra(String result) {
        JSONObject resultJSON = jsonSupplier.get();
        JSONObject object = new JSONObject(result);
        SearchService.runDijkstraPost(null, object, resultJSON);
        return resultJSON.toString();
    }
}