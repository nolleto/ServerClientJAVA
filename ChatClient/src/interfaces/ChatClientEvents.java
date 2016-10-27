/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import chatclient.User;
import java.util.List;

/**
 *
 * @author Felipe
 */
public interface ChatClientEvents {
    String requestNickname();
    void connected(String nickname);
    void receivedMessage(String message, String from);
    void users(List<User> users);
    void userConnected(User users);
    void userDisconnected(User users);
    void warned();
}
