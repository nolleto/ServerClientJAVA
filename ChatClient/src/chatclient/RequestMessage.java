/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import com.google.gson.Gson;

/**
 *
 * @author Felipe
 */
public class RequestMessage {
    private String type;
    private String body;
    private String from;
    
    public static RequestMessage fromString(String message) {
        Gson g = new Gson();
        return g.fromJson(message, RequestMessage.class);
    }

    public String getFrom() {
        return from;
    }
    
    public String getBody() {
        return body;
    }

    public String getType() {
        return type;
    }
}
