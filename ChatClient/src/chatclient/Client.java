/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author 0094078
 */
public class Client implements Runnable {
    Socket socket;
    PrintWriter out;
    BufferedReader in;
    Thread thread;
    
    public Client(Socket socket) {
        this.socket = socket;
        setup();
        start();
    }
    
    private void setup() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void sendMessage(String message) {
        out.println(message);
    }
    
    public void waitForMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) { 
                System.out.println("Client received: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
