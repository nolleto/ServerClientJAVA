/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import chatserver.ChatServer;
import java.io.IOException;
import java.net.Socket;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 0094078
 */
public class ServerUnitTest {
    private ChatServer server;
            
    public ServerUnitTest() { 
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void createServer() throws IOException {
        server = new ChatServer(8088);
        
        assertNotNull(server);
    }
    
    /*@Test
    public void connectServer() throws IOException {
        System.out.println(server == null);
        Socket client = new Socket("127.0.0.1", 8088);
        
        assertNotNull(client);
    }*/
}
