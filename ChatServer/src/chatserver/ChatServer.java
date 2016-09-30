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
    static List<ConnectedClient> clients;
    
    public ChatServer(int port) throws IOException {
        ServerSocket server = new ServerSocket(port);
        clients = new ArrayList<>();
        waitForClients(server);
    }

    static void waitForClients(ServerSocket server) {
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
