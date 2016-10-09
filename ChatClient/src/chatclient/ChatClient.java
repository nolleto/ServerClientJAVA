/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import com.google.gson.Gson;
import interfaces.ChatClientEvents;
import java.io.*;
import java.net.*;
import java.util.List;

/**
 *
 * @author Felipe
 */
public class ChatClient implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Thread thread;
    private ChatClientEvents events;
    private String nickname;
    
    public ChatClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
        setup();
        start();
    }
    
    public void setEvents(ChatClientEvents events) {
        this.events = events;
    }
    
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void setup() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.events = new ChatClientEvents() {
            @Override
            public String requestNickname() {
                try {
                    Thread.sleep(100);
                } catch (Exception e){
                    
                }
                return "";
            }

            @Override
            public void connected(String nickname) {
                
            }

            @Override
            public void receivedMessage(String message, String from) {
                
            }

            @Override
            public void users(List<User> users) {
                
            }

            @Override
            public void userConnected(User users) {
                
            }

            @Override
            public void userDisconnected(User users) {
                
            }
        };
    }
    
    private void start() {
        thread = new Thread(this);
        thread.start();
    }
    
    @Override
    public void run() {
        try {
            String message;
            while ((message = in.readLine()) != null) { 
                RequestMessage request = RequestMessage.fromString(message);
                String type = request.getType();
                
                if (type.equals("message")) {
                    events.receivedMessage(request.getBody(), request.getFrom());
                    
                } else if (type.equals("nickname")) {
                    nickname = events.requestNickname();
                    sendMessage(new ResponseMessage(type, nickname));
                    
                } else if (type.equals("users")) {
                    List<User> users = User.listFromString(request.getBody());
                    events.users(users);
                    
                } else if (type.equals("userConnected")) {
                    User user = User.fromString(request.getBody());
                    events.userConnected(user);
                    
                } else if (type.equals("userDisconnected")) {
                    User user = User.fromString(request.getBody());
                    events.userDisconnected(user);
                    
                } else if (type.equals("connected")) {
                    events.connected(nickname);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void sendMessage(String message) {
        out.println(message);
    }
    
    public void sendMessage(ResponseMessage message) {
        Gson g = new Gson();
        out.println(g.toJson(message));
    }
    
    public void waitForMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) { 
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
