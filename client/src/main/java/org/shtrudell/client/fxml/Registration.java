package org.shtrudell.client.fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.shtrudell.client.fxml.items.DocumentCellFactory;

public class Registration {
    @FXML
    private PasswordField repeatPasswordField;
    @FXML
    private Button singupButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void initialize() {
    }

    public void singupAction(ActionEvent actionEvent) {
    }
}
