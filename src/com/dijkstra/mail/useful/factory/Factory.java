package com.dijkstra.mail.useful.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.bson.Document;
import org.json.JSONObject;

import com.dijkstra.mail.controller.user.UserController;

public final class Factory {

    private Factory() {}

    public static final Supplier<Map<String, String>> simpleHash = HashMap<String, String>::new;
    public static final Supplier<Map<String, Map<String, String>>> complexHash =
            HashMap<String, Map<String, String>>::new;
    public static final Supplier<JSONObject> jsonSupplier = JSONObject::new;
    public static final Supplier<Document> docSupplier = Document::new;
    public static final Supplier<UserController> controllerSupplier = UserController::new;
}
