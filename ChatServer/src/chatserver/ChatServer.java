/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Felipe
 */
public class ChatServer {
    static List<Client> clients;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(8088);
            clients = new ArrayList<>();
            waitForClients(server);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void waitForClients(ServerSocket server) {
      try {
          while (true) {
              Socket s = server.accept();
              Client client = new Client(s);
              clients.add(client);
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
    }
}
