package com.dijkstra.mail.restservice;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;

import com.dijkstra.mail.factory.Factory;
import com.dijkstra.mail.service.SearchService;

 
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