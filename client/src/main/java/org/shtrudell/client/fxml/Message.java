package org.shtrudell.client.fxml;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class Message {
    public EventHandler<ActionEvent> closeEventHandler;
    @FXML
    private Text messageText;

    public void setMessage(String message) {
        messageText.setText(message);
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
