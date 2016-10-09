/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import com.google.gson.Gson;
import java.util.UUID;

/**
 *
 * @author Felipe
 */
public class User {
    private String id;
    private String name;

    public User(UUID id, String name) {
        this.id = id.toString();
        this.name = name;
    }
    
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        Gson g = new Gson();
        return g.toJson(this);
    }
    
    
}
