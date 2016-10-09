/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Felipe
 */
public class User {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public static User fromString(String str) {
        Gson g = new Gson();
        return g.fromJson(str, User.class);
    }
    
    public static List<User> listFromString(String str) {
        java.lang.reflect.Type listType = new TypeToken<ArrayList<User>>(){}.getType();
        Gson g = new Gson();
        return g.fromJson(str, listType);
    }
}
