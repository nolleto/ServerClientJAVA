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

/**
 *
 * @author Felipe
 */
public class Client implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    Thread thread;

    public Client(Socket socket) {
        this.socket = socket;
        setup();
        //sendMessage("ok");
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
            String inString;
            // read the command from the client
            while  ((inString = in.readLine()) == null);
            System.out.println("Read command " + inString);
            /*while(true){
                String message = getMessage();

                System.out.println(message);
                for(Client c: ChatServer.clients){
                    c.sendMessage(message);
                }
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private String getMessage() throws IOException {
        return in.readLine();
    }
    
    public void sendMessage(String message) {
        out.println(message);
    }
}
