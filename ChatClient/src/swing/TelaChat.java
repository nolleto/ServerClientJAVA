/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing;

import chatclient.ChatClient;
import chatclient.ResponseMessage;
import chatclient.User;
import interfaces.ChatClientEvents;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Felipe
 */
public class TelaChat extends javax.swing.JFrame {
    private ChatClient chatClient;
    private DefaultListModel<String> userModel;
    private List<User> userConnecteds;

    /**
     * Creates new form TelaChat
     */
    public TelaChat(ChatClient chatClient) {
        this.chatClient = chatClient;
        initComponents();
        setup();
    }
    
    private void setup() {
        userModel = new DefaultListModel<String>();
        userConnecteds = new ArrayList<>();
        usersList.setModel(userModel);
        DefaultCaret caret = (DefaultCaret)messagesTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        chatClient.setEvents(new ChatClientEvents() {
            @Override
            public String requestNickname(String message) {
                return JOptionPane.showInputDialog(TelaChat.this,
                    message,
                    "Nickname",
                    JOptionPane.PLAIN_MESSAGE);
            }

            @Override
            public void connected(String nickname) {
                myMessageTextArea.setEnabled(true);
                usersList.setEnabled(true);
                messagesTextArea.setEditable(false);
                messagesTextArea.setEnabled(true);
                jLabel1.setText(jLabel1.getText() + " - " + nickname);
            }
            
            @Override
            public void receivedMessage(String message, String from) {
                User userFrom = find(from);
                updateChatMessages(userFrom.getName(), message);
            }

            @Override
            public void users(List<User> users) {
                for (User user : users) {
                    userModel.addElement(user.getName());
                    userConnecteds.add(user);
                }
            }

            @Override
            public void userConnected(User user) {
                userModel.addElement(user.getName());
                userConnecteds.add(user);
            }

            @Override
            public void userDisconnected(User user) {
                userModel.removeElement(user.getName());
                
                
                for (User userConnected : userConnecteds) {
                    if (user.getId().equals(userConnected.getId())) {
                        userConnecteds.remove(userConnected);
                        break;
                    }
                }
            }

            @Override
            public void warned() {
                JOptionPane.showMessageDialog(TelaChat.this,
                    "Você está inativo! \nRealize uma ação ou será disconectado em breve!"); 
            }
            @Override
            public void disconnected() {
                JOptionPane.showMessageDialog(TelaChat.this, "Nós decidimos desconecta-lo! \nPedimos desculpas... :(");
                TelaChat.this.dispose();
                new TelaIniciarChat().setVisible(true);
            }
        });
        
        usersList.setSelectionModel(new DefaultListSelectionModel() {
            private static final long serialVersionUID = 1L;
            boolean gestureStarted = false;

            @Override
            public void setSelectionInterval(int index0, int index1) {
                if(!gestureStarted){
                    if (isSelectedIndex(index0)) {
                        super.removeSelectionInterval(index0, index1);
                    } else {
                        super.addSelectionInterval(index0, index1);
                    }
                }
                gestureStarted = true;
            }

            @Override
            public void setValueIsAdjusting(boolean isAdjusting) {
                if (isAdjusting == false) {
                    gestureStarted = false;
                }
            }
        });
        
        /**
         * Enable/disables Send button whether input has text
         **/
        myMessageTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                valid();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                valid();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                valid();
            }
            
            public void valid() {
                sendButton.setEnabled(!myMessageTextArea.getText().isEmpty());
            }
        });
    }
    
    /**
    * Retrieves a User from the list of connected users by a given id
    **/
    private User find(String id) {
        for (User u : userConnecteds) {
            if (u.getId().equals(id)) return u;
        }
        
        return null;
    }
    
    private void updateChatMessages(String senderName, String message) {
        String currentText = messagesTextArea.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/HH/yyyy HH:mm:ss");
        String newMsg = String.format("[%s] %s: %s", sdf.format(new Date()), senderName,  message);
        String newTextToAppend = currentText + "\n" + newMsg;
        messagesTextArea.setText(newTextToAppend); 
    } 
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        usersList = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        sendButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        myMessageTextArea = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        messagesTextArea = new javax.swing.JTextArea();
        pongButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Chat");

        usersList.setEnabled(false);
        jScrollPane1.setViewportView(usersList);

        jLabel2.setText("Mensagem:");

        jLabel3.setText("Usuários:");

        sendButton.setText("Enviar");
        sendButton.setEnabled(false);
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        myMessageTextArea.setColumns(20);
        myMessageTextArea.setRows(5);
        myMessageTextArea.setEnabled(false);
        jScrollPane3.setViewportView(myMessageTextArea);

        messagesTextArea.setColumns(20);
        messagesTextArea.setRows(5);
        messagesTextArea.setEnabled(false);
        jScrollPane4.setViewportView(messagesTextArea);

        pongButton.setLabel("Pong Server");
        pongButton.setName("pongButton"); // NOI18N
        pongButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pongButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sendButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pongButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sendButton)
                    .addComponent(pongButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Performs the action of clicking Send button
     **/
    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        int[] indexes = usersList.getSelectedIndices();
        List<String> ids = new ArrayList<>();
        String msg = myMessageTextArea.getText().trim();
        
        if (msg.isEmpty()) return;
            
        for (int index : indexes) {
            User u = userConnecteds.get(index);
            ids.add(u.getId());
        }
        
        updateChatMessages(chatClient.getNickname(), msg);
        chatClient.sendMessage(new ResponseMessage("message", msg, ids));
        myMessageTextArea.setText("");
    }//GEN-LAST:event_sendButtonActionPerformed

    private void pongButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pongButtonActionPerformed
        chatClient.sendMessage(new ResponseMessage("pong"));
    }//GEN-LAST:event_pongButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea messagesTextArea;
    private javax.swing.JTextArea myMessageTextArea;
    private javax.swing.JButton pongButton;
    private javax.swing.JButton sendButton;
    private javax.swing.JList<String> usersList;
    // End of variables declaration//GEN-END:variables
}
