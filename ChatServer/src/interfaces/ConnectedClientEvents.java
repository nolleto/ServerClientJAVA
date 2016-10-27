/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import chatserver.ConnectedClient;
import chatserver.RequestMessage;

/**
 *
 * @author Felipe
 */
public interface ConnectedClientEvents {
    void createdNickname(String nickname);
    void messageReceived(RequestMessage message);
    void disconnected();
    void pong(ConnectedClient client);
}
