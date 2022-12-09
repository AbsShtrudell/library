package org.shtrudell.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.shtrudell.client.fxml.Login;
import org.shtrudell.client.fxml.MainWindow;
import org.shtrudell.client.fxml.Registration;
import org.shtrudell.client.net.Client;
import org.shtrudell.client.net.DataController;


import java.io.IOException;

public class MainApplication extends Application {
    private static DataController dataController;
    private static Stage stage = null;
    private static Client client = null;

    public static DataController getDataController() {
        return dataController;
    }

    @Override
    public void start(Stage stage) throws IOException {
        MainApplication.client = new Client();
        client.connect("127.0.0.1", 50);
        dataController = client.getDataController();
        MainApplication.stage = stage;
        changeToLoginWindow();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Parent createLoginWindow(DataController dataController) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/Login.fxml"));
        try {
            Parent login = fxmlLoader.load();
            Login controller = fxmlLoader.getController();
            controller.setSingUpEvent(e-> changeToRegisterWindow());
            controller.setSuccessfulLoginEvent(e-> changeToMainWindow());
            return login;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Parent createRegisterWindow(DataController dataController) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/Registration.fxml"));
        try {
            Parent registration = fxmlLoader.load();
            Registration controller = fxmlLoader.getController();
            controller.setSingupEvent(e-> changeToLoginWindow());
            controller.setBackEvent(e-> changeToLoginWindow());
            return registration;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Parent createMainWindow(DataController dataController) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("fxml/MainWindow.fxml"));
        try {
            Parent mainWindow = fxmlLoader.load();
            MainWindow controller = fxmlLoader.getController();
            controller.setCloseEvent(e-> close());
            controller.setLogOutEvent(e-> changeToLoginWindow());
            return mainWindow;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void changeToLoginWindow() {
        stage.setScene(new Scene(createLoginWindow(client.getDataController())));
    }

    private static void changeToRegisterWindow() {
        stage.setScene(new Scene(createRegisterWindow(client.getDataController())));
    }

    private static void changeToMainWindow() {
        stage.setScene(new Scene(createMainWindow(client.getDataController())));
    }

    private static void close() {
        stage.close();
    }
}