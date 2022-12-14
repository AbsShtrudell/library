package org.shtrudell.client.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.shtrudell.client.ClientApp;
import org.shtrudell.client.fxml.Message;

import java.io.IOException;
import java.util.Objects;

public class AlertBox {

    public static void display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setResizable(false);

        FXMLLoader fxmlLoader = new FXMLLoader(ClientApp.class.getResource("fxml/Message.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());

            try {
                window.getIcons().add(new Image(Objects.requireNonNull(ClientApp.class.getResourceAsStream("/org/shtrudell/client/icon_warning.png"))));
            }
            catch (Exception ignored) {
            }

        Message controller = fxmlLoader.getController();
        controller.setMessage(message);
        controller.setCloseEvent(e -> window.close());
        window.setScene(scene);
        window.showAndWait();
        } catch (IOException ignored) {
        }
    }
}
