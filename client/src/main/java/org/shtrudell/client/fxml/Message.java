package org.shtrudell.client.fxml;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import lombok.Setter;

public class Message {
    @FXML
    private Text messageText;
    @Setter
    private EventHandler<ActionEvent> closeEvent;

    public void setMessage(String message) {
        messageText.setText(message);
    }

    @FXML
    private void closeAction(ActionEvent actionEvent) {
        if(closeEvent != null)
            closeEvent.handle(null);
    }
}
