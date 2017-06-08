package com.dijkstra.mail.view;

import com.dijkstra.mail.useful.factory.Factory;
import com.dijkstra.mail.controller.SearchService;
import org.json.JSONObject;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

 
@Path("/dijkstra")

public class RestService {
	
	@POST
    @Produces(MediaType.APPLICATION_JSON)	
	public String dijkstra(String result) {		
		JSONObject resultJSON = Factory.JSONSUPPLIER.get();
		JSONObject object = new JSONObject(result);
		SearchService.runDijkstraPost(null, object, resultJSON);
		return resultJSON.toString();	
	}	
}