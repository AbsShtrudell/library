package org.shtrudell.server.net;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server implements Closeable{
    private static final int LINGER_TIME = 5000;
    private static final int TIMEOUT_HALF_HOUR = 1800000;
    private final List<ClientHandler> clientHandlers = new ArrayList<>();
    private int port = 50;
    private boolean closed = false;

    public Server(int port) {
        this.port = port;
    }

    public void start(){
        try {
            ServerSocket listeningSocket = new ServerSocket(port);
            while (!this.closed) {
                Socket clientSocket = listeningSocket.accept();
                System.out.println("New client connected");
                startClientHandler(clientSocket);
            }
            listeningSocket.close();
        } catch (IOException e) {
            System.err.println("Server failure!");
        } finally {
            for(var handler : clientHandlers) {
                handler.close();
                removeClientHandler(handler);
            }
        }
    }

    private void startClientHandler(Socket clientSocket) throws SocketException {
        clientSocket.setSoLinger(true, LINGER_TIME);
        clientSocket.setSoTimeout(TIMEOUT_HALF_HOUR);
        ClientHandler handler = new ClientHandler(clientSocket, new ClientHandlerListener());
        synchronized (clientHandlers) {
            clientHandlers.add(handler);
        }
        Thread handlerThread = new Thread(handler);
        handlerThread.setPriority(Thread.MAX_PRIORITY);
        handlerThread.start();
    }

    private void removeClientHandler(ClientHandler handler) {
        synchronized (clientHandlers) {
            clientHandlers.remove(handler);
        }
    }

    @Override
    public void close() {
        this.closed = true;
    }

    class ClientHandlerListener {
        public void onDisconnected(ClientHandler handler) {
            removeClientHandler(handler);
        }
    }
}
