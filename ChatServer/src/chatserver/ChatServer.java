/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import interfaces.ChatServerEvents;
import interfaces.ConnectedClientEvents;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author Felipe
 */
public class ChatServer {
    private ServerSocket server;
    private Thread thread;
    private ChatServerEvents events;
    public static List<ConnectedClient> clients;
    
    public ChatServer(int port) throws IOException {
        server = new ServerSocket(port);
        clients = new ArrayList<>();
        thread = new Thread(() -> {
            waitForClients();
        });
        thread.start();
    }

    public void setChatServerEvents(ChatServerEvents chatServerEvents) {
        this.events = chatServerEvents;
    }

    public List<ConnectedClient> getClients() {
        return clients.stream()
                .filter((x) -> x.isConnected())
                .collect(Collectors.toList());
    }
    
    public void disconnect(String clientNickname) {
        Optional<ConnectedClient> opt = clients.stream()
                .filter((x) -> x.isConnected() && x.getNickname().equals(clientNickname))
                .findFirst();
        
        if(opt.isPresent()) {
            ConnectedClient client = opt.get();
            client.killConnection();
            events.clientDisconnected(client);
        } else {
            events.errorDisconnecting(clientNickname);
        }
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
              if (events != null) {
                  events.clientConnecting();
              }
              
              ConnectedClient client = new ConnectedClient(s);
              
              client.setEvents(new ConnectedClientEvents() {
                  @Override
                  public void createdNickname(String nickname) {
                      if (events != null) {
                          events.clientConnected(client);
                          client.updateAtivity();
                      }
                  }

                  @Override
                  public void messageReceived(RequestMessage message) {
                      if (events != null) {
                          client.updateAtivity();
                      }
                  }

                  @Override
                  public void disconnected() {
                      if (events != null) {
                          events.clientDisconnected(client);
                      }
                      ChatServer.clients.remove(client);
                  }

                  @Override
                  public void pong(ConnectedClient client) {
                      if (events != null) {
                          events.clientPong(client);
                      }
                  }
              });
              
              clients.add(client);
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
    }
}
