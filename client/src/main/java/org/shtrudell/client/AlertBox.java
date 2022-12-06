package org.shtrudell.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.shtrudell.client.fxml.Message;

import java.io.IOException;

public class AlertBox {

    public static void display(String title, String message) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setResizable(false);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/Message.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Message controller = fxmlLoader.getController();
        controller.setMessage(message);
        controller.setOnClose(e -> window.close());
        window.setScene(scene);
        window.showAndWait();
    }

}
