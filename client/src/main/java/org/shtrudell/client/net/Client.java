package org.shtrudell.client.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.shtrudell.common.net.AnswerMessage;
import org.shtrudell.common.net.MessageMethod;
import org.shtrudell.common.net.QueryMessage;

public class Client {
    private static final int TIMEOUT_HALF_HOUR = 1800000;
    private static final int TIMEOUT_HALF_MINUTE = 30000;
    private Socket socket;
    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;
    private boolean connected;

    public void connect(String host, int port) throws IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(host, port), TIMEOUT_HALF_MINUTE);
        socket.setSoTimeout(TIMEOUT_HALF_HOUR);
        connected = true;
        toServer = new ObjectOutputStream(socket.getOutputStream());
        fromServer = new ObjectInputStream(socket.getInputStream());
    }

    public void disconnect(boolean lostConnection) {
        try {
            if(!lostConnection) {
                sendMsg(QueryMessage.builder().
                        method(MessageMethod.DISCONNECT).
                        build());
            }

            socket.close();
            socket = null;
            connected = false;
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public AnswerMessage receiveMsg() {
        AnswerMessage msg = null;
        while (msg == null) {
            try {
                msg = (AnswerMessage) fromServer.readObject();
            } catch (IOException ioe) {
                if (connected) {
                    System.out.println("Lost connection.");
                    disconnect(true);
                }
            } catch (ClassNotFoundException ignored) {
            }
        }
        return msg;
    }

    public void sendMsg(QueryMessage msg) {
        try {
            toServer.writeObject(msg);
            toServer.flush();
            toServer.reset();
        } catch (IOException ioe) {
            System.out.println("Can't send message");
        }
    }
}
