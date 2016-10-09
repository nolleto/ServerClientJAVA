/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import com.google.gson.Gson;
import java.util.List;

/**
 *
 * @author Felipe
 */
public class RequestMessage {
    private String type;
    private String body;
    private List<String> adresseds;

    public RequestMessage(String type) {
        this.type = type;
    }
    
    public RequestMessage(String type, String body, List<String> adresseds) {
        this.type = type;
        this.body = body;
        this.adresseds = adresseds;
    }
    
    public static RequestMessage fromString(String message) {
        Gson g = new Gson();
        return g.fromJson(message, RequestMessage.class);
    }
    
    public List<String> getAdresseds() {
        return adresseds;
    }

    public String getBody() {
        return body;
    }

    public String getType() {
        return type;
    }
}
