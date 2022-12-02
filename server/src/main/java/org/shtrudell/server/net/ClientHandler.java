package org.shtrudell.server.net;

import org.shtrudell.common.net.AnswerMessage;
import org.shtrudell.common.net.QueryMessage;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable, Closeable {
    private final Server.ClientHandlerListener listener;
    private final MessageHandler messageHandler;
    private final Socket clientSocket;
    private ObjectInputStream fromClient;
    private ObjectOutputStream toClient;
    private boolean connected = true;
    private boolean closed = false;

    public ClientHandler(Socket clientSocket, Server.ClientHandlerListener listener) {
        this.clientSocket = clientSocket;
        this.listener = listener;

        messageHandler = new MessageHandler();
    }

    @Override
    public void run() {
        try {
            fromClient = new ObjectInputStream(clientSocket.getInputStream());
            toClient = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
            close();
        }
        while (connected && !closed) {
            try {
                QueryMessage queryMessage = receiveMsg();
                AnswerMessage msg = messageHandler.handleMessage(queryMessage);
                if(msg == null) continue;
                sendMsg(msg);
            } catch (IOException e) {
                System.out.println("Client Disconnected");
                close();
            } catch (ClassNotFoundException e) {
                System.out.println("Corrupted message");
                close();
            } catch (Exception e) {
                System.out.println("Unknown Error! Client Disconnected");
                close();
            }
        }
        disconnectClient();
        System.out.println("Client handler closed");
    }

    @Override
    public void close() {
        closed = true;
    }

    private void sendMsg(AnswerMessage msg) throws IOException {
        toClient.writeObject(msg);
        toClient.flush();
        toClient.reset();
    }

    private QueryMessage receiveMsg() throws IOException, ClassNotFoundException {
        return (QueryMessage) fromClient.readObject();
    }

    private void disconnectClient() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connected = false;
        listener.onDisconnected(this);
    }
}
