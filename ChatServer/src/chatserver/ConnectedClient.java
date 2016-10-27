/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import com.google.gson.Gson;
import interfaces.ConnectedClientEvents;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author Felipe
 */
public class ConnectedClient implements Runnable {
    private UUID id;
    private String nickname;
    private boolean connected;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Thread thread;
    private ConnectedClientEvents events;
    private Date lastMessageTime;
    private boolean warned;

    public ConnectedClient(Socket socket) {
        this.socket = socket;
        this.id = UUID.randomUUID();
        setup();
        startListennig();
    }

    public void setEvents(ConnectedClientEvents events) {
        this.events = events;
    }

    public  void close() {
        connected = false;
        try {
            socket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        if (events != null) {
            events.disconnected();
        }
    }
    
    private void setup() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startListennig() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            requestNicknameClient();
            onConnected();
            
            String msgStr;
            while ((msgStr = in.readLine()) != null) { 
                RequestMessage request = RequestMessage.fromString(msgStr);
                String type = request.getType();
                updateAtivity();
                if (type.equals("message")) {
                    List<String> adresseds = request.getAdresseds();
                    for (ConnectedClient client : ChatServer.clients) {
                        if (client.equals(this)) continue;
                        if (adresseds.isEmpty() || adresseds.contains(client.getId().toString())) {
                            client.sendMessage(new ResponseMessage(type, request.getBody(), this.getId().toString()));
                        }
                    }
                }
            }
            
        } catch (IOException e) {
            //Desconectou
        }
        close();
    }
    
    private List<String> getClientNames(String message) {
        ArrayList<String> result = new ArrayList<>();
        Pattern p = createPatternUser();
        Matcher userMatcher = p.matcher(message);
        int i = 0;
        
        while (userMatcher.find()) {
            String userName = userMatcher.group(i);
            userName = userName.replaceAll("@", "");
            result.add(userName);
        }

        return result;
    }
    
    private List<ConnectedClient> getClients(List<String> userNames) {
        ArrayList<ConnectedClient> result = new ArrayList<>();
        
        for (ConnectedClient c: ChatServer.clients){
            if (c.getId().equals(getId())) continue;
            
            if (userNames.isEmpty() || userNames.contains(c.getNickname())) {
                result.add(c);
            }
        }
        
        return result;
    }
    
    private Pattern createPatternUser() {
      String pattern = "@[A-Za-z_]*";
      return Pattern.compile(pattern);
    }
    
    private void onConnected() {
        sendMessage(new ResponseMessage("connected"));
        connected = true;
        List<User> users = new ArrayList<>();
        Gson g = new Gson();
        User current = new User(id, nickname);
        
        for (ConnectedClient c : ChatServer.clients) {
            if (c.isConnected() && !c.getId().equals(ConnectedClient.this.id)) {
                c.sendMessage(new ResponseMessage("userConnected", g.toJson(current)));
                users.add(new User(c.getId(), c.getNickname()));
            }
        }
        
        sendMessage(new ResponseMessage("users", g.toJson(users)));
    }
    
    private void requestNicknameClient() throws IOException {
        while (true) { 
            sendMessage(new ResponseMessage("nickname"));
            String message = null;
            message = in.readLine();
            RequestMessage request = RequestMessage.fromString(message);
            String nick = request.getBody();
            
            if (nick != null && !nick.isEmpty() && !nicknameExists(nick)) {
                nickname = nick;
                if (events != null) {
                    events.createdNickname(nickname);
                    updateAtivity();
                }
                break;
            }
        }
    }
    
    private boolean nicknameExists(String n) {
        boolean result = false;
        n = n.toLowerCase();
        
        for (ConnectedClient c : ChatServer.clients) {
            if (c.getId().equals(getId()) || !c.isConnected()) continue;
            if (c.getNickname().toLowerCase().equals(n)) return true;
        }
        
        return result;
    }
    
    private String getMessage() throws IOException {
        return in.readLine();
    }
    
    public void sendMessage(ResponseMessage message) {
        Gson gson = new Gson();
        String msg = gson.toJson(message);
        out.println(msg);
    }
    
    public void sendMessage(String message) {
        out.println(message);
    }
    
    public void updateAtivity() {
        setLastMessageTime(Calendar.getInstance().getTime());
        setWarned(false);
    }

    public UUID getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isConnected() {
        return connected;
    }
    
    public void setLastMessageTime(Date date) {
        this.lastMessageTime = date;
    }
    
    public Date getLastMessageTime() {
        return lastMessageTime;
    }
    
    public void setWarned(boolean warned) {
        this.warned = warned;
    }
    
    public boolean wasWarned() {
        return warned;
    }
}
