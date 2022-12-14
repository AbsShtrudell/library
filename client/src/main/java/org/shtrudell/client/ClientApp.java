package org.shtrudell.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.shtrudell.client.fxml.Login;
import org.shtrudell.client.fxml.MainWindow;
import org.shtrudell.client.fxml.Registration;
import org.shtrudell.client.net.Client;
import org.shtrudell.client.net.DataController;
import org.shtrudell.client.util.AlertBox;
import org.shtrudell.client.util.FXMLHelper;


import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ClientApp extends Application {
    private static DataController dataController;
    private static Stage stage = null;
    private static Client client = null;
    public static DataController getDataController() {
        return dataController;
    }

    @Override
    public void start(Stage stage) throws IOException {
        int port = 50;
        String ip = "127.0.0.1";

        try {
            ResourceBundle resource = ResourceBundle.getBundle("client", Locale.getDefault());
            port = Integer.parseInt(resource.getString("client.port"));
            ip = resource.getString("client.ip");
        } catch (MissingResourceException e) {
            System.out.println("Can't find config file");
        } catch (NumberFormatException e) {
            System.out.println("Wrong port value");
        }

        try {
            ClientApp.client = new Client();
            client.connect(ip, port);
        }
        catch (IOException e) {
            AlertBox.display("Ошибка", "Невозможно установить соединение с сервером");
            return;
        }

        dataController = client.getDataController();
        ClientApp.stage = stage;
        changeToLoginWindow();

        try {
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/shtrudell/client/icon_book.png"))));
        }
        catch (Exception ignored) {
        }

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Parent createLoginWindow(DataController dataController) {
        FXMLLoader fxmlLoader = FXMLHelper.makeLoader("Login");
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
        FXMLLoader fxmlLoader = FXMLHelper.makeLoader("Registration");
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
        FXMLLoader fxmlLoader = FXMLHelper.makeLoader("MainWindow");
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