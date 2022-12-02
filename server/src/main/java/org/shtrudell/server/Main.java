package org.shtrudell.server;


import org.shtrudell.server.integration.StandardDaoImp;
import org.shtrudell.server.model.Role;
import org.shtrudell.server.net.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}