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
            String sentence;
            String modifiedSentence;
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

            InetAddress ia = InetAddress.getByName(null);
            Socket socket = new Socket(ia, 8088);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            out.printf("olar");
            
            String inputLine;
            while ((inputLine = in.readLine()) != null) { 
                System.out.println(inputLine);
            }
            
            socket.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
