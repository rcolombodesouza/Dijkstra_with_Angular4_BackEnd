package com.dijkstra.mail.factory;

import com.dijkstra.mail.controller.usercotroller.UserController;
import org.bson.Document;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


public interface Factory {
	
	Supplier<Map<String, String>> SIMPLEHASH = HashMap<String, String>::new;
	Supplier<Map<String, Map<String, String>>> COMPLEXHASH = HashMap<String, Map<String, String>>::new;
	Supplier<JSONObject> JSONSUPPLIER = JSONObject::new;
	Supplier<Document> DOCSUPPLIER = Document::new;
	Supplier<UserController> CONTROLLERSUPPLIER = UserController::new;
	
}
