package org.shtrudell.client.fxml;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import lombok.Setter;
import org.shtrudell.client.net.DataController;

public class Admin {
    @Setter
    private EventHandler<ActionEvent> closeEvent;

    @FXML
    private void closeAction(ActionEvent actionEvent) {
        if(closeEvent != null)
            closeEvent.handle(null);
    }
}
