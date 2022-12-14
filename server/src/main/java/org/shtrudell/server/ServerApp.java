package org.shtrudell.server;


import org.shtrudell.server.net.Server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public class ServerApp {
    public static void main(String[] args) {
        int port = 50;
        try {
            ResourceBundle resource = ResourceBundle.getBundle("server", Locale.getDefault());
            port = Integer.parseInt(resource.getString("server.port"));
        } catch (MissingResourceException e) {
            System.out.println("Can't find config file");
        } catch (NumberFormatException e) {
            System.out.println("Wrong port value");
        }
        Server server = new Server(port);
        server.start();
    }
}