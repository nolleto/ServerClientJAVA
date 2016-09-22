/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.io.*;
import java.net.*;

/**
 *
 * @author Felipe
 */
public class ChatClient {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            InetAddress ia = InetAddress.getByName(null);
            Socket socket = new Socket(ia, 8088);
            
            Client client = new Client(socket);
            client.sendMessage("Teste");
            client.waitForMessages();
            
            socket.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
