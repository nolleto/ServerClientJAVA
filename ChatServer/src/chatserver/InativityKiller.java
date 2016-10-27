/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author i851463
 */
public class InativityKiller implements Runnable{
    
    private long quantum;
    private ChatServer server;
    private long maximumInativityTime;
    
    public InativityKiller(ChatServer server) {
        this.server = server;
        this.quantum = 10000L;
        this.maximumInativityTime = 20000L;
        System.out.println("Criei");
    }
    
    @Override
    public void run() {
        while(true){
            try {
                findSleepers();
                System.out.println("Limpei");
                Thread.sleep(quantum);
            } catch (InterruptedException ex) {
                Logger.getLogger(InativityKiller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void findSleepers() {
        for (ConnectedClient client : server.getClients()) {
            if(client.wasWarned()) {
                client.killConnection();
            } else {
                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();
                
                Date lastMessage = client.getLastMessageTime();
                if ((currentDate.getTime()-lastMessage.getTime()) > maximumInativityTime) {
                    client.sendMessage(new ResponseMessage("warning"));
                    client.setWarned(true);
                }
            }
        }
    }
}
