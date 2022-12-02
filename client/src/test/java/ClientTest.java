import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.shtrudell.client.net.Client;

import java.io.IOException;

public class ClientTest {
    Client client;

    @Before
    public void connect() {
        client = new Client();
        try {
            client.connect("127.0.0.1", 55);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void disconnect() {
        client.disconnect(false);
    }

    @Test
    public void receiveMsg() {
         
    }

    @Test
    public void sendMsg() {
    }
}
