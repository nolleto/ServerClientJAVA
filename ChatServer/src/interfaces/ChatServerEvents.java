/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import chatserver.ConnectedClient;

/**
 *
 * @author Felipe
 */
public interface ChatServerEvents {
    void clientConnecting();
    void clientConnected(ConnectedClient client);
    void clientDisconnected(ConnectedClient client);
    void errorDisconnecting(String nickname);
}
