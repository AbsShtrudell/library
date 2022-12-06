package org.shtrudell.client.fxml;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

public class Admin {

    private EventHandler<ActionEvent> closeEvent;
    @FXML
    private void closeAction(ActionEvent actionEvent) {
        if(closeEvent != null)
            closeEvent.handle(null);
    }

    public void setCloseEvent(EventHandler<ActionEvent> closeEvent) {
        this.closeEvent = closeEvent;
    }
}
