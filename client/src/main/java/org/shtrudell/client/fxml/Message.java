package org.shtrudell.client.fxml;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Message {
    @FXML
    private Label messageLabel;

    public EventHandler<ActionEvent> closeEventHandler;

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    public void setOnClose(EventHandler<ActionEvent> closeEvent) {
        closeEventHandler = closeEvent;
    }

    @FXML
    private void closeAction(ActionEvent actionEvent) {
        if(closeEventHandler != null)
            closeEventHandler.handle(null);
    }
}
