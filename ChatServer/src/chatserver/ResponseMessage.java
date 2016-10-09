/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import com.google.gson.Gson;

/**
 *
 * @author Felipe
 */
public class ResponseMessage {
    private String type;
    private String body;
    private String from;

    public ResponseMessage(String type) {
        this.type = type;
    }
    
    public ResponseMessage(String type, String body) {
        this.type = type;
        this.body = body;
    }
    
    public ResponseMessage(String type, String body, String from) {
        this.type = type;
        this.body = body;
        this.from = from;
    }
    
    public static ResponseMessage fromString(String message) {
        Gson g = new Gson();
        return g.fromJson(message, ResponseMessage.class);
    }
    
    public String getBody() {
        return body;
    }

    public String getType() {
        return type;
    }

    public String getFrom() {
        return from;
    }
}
