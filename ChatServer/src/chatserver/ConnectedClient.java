/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Felipe
 */
public class ConnectedClient implements Runnable {
    private UUID id;
    private String nickname;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Thread thread;

    public ConnectedClient(Socket socket) {
        this.socket = socket;
        this.id = UUID.randomUUID();
        setup();
        startListennig();
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
            getNicknameClient();
            
            String message;
            while ((message = in.readLine()) != null) { 
                String msg = String.format("%s: %s", nickname, message);
                List<String> names = getClientNames(msg);
                List<ConnectedClient> clients = getClients(names);
                
                for (ConnectedClient c: clients){
                    c.sendMessage(msg);
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        ChatServer.clients.remove(this);
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
    
    private void getNicknameClient() throws IOException {
        nickname = in.readLine();
    }
    
    private String getMessage() throws IOException {
        return in.readLine();
    }
    
    public void sendMessage(String message) {
        out.println(message);
    }

    public UUID getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }
    
    
}
