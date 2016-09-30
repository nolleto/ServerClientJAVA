/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Felipe
 */
public class ChatServer {
    private ServerSocket server;
    private Thread thread;
    public static List<ConnectedClient> clients;
    
    public ChatServer(int port) throws IOException {
        server = new ServerSocket(port);
        clients = new ArrayList<>();
        thread = new Thread(() -> {
            waitForClients();
        });
        thread.start();
    }

    public void close() throws IOException {
        thread.interrupt();
        clients.stream().forEach((client) -> {
            client.close();
        });
        server.close();
        
        thread = null;
        clients = null;
        server = null;
    }
    
    private void waitForClients() {
      try {
          while (true) {
              Socket s = server.accept();
              ConnectedClient client = new ConnectedClient(s);
              clients.add(client);
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
    }
}
