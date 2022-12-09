package org.shtrudell.client.fxml;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.shtrudell.client.AlertBox;
import org.shtrudell.client.MainApplication;
import org.shtrudell.client.net.DataController;
import org.shtrudell.common.model.UserDTO;
import org.shtrudell.common.util.HashPassword;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

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

    private EventHandler<ActionEvent> singupEvent;
    private EventHandler<ActionEvent> backEvent;

    @FXML
    private void initialize() {
    }

    public void singupAction(ActionEvent actionEvent) {
        if(loginField.getText() == null || loginField.getText().isBlank() ||
                passwordField.getText() == null || passwordField.getText().isBlank() ||
                repeatPasswordField.getText() == null || repeatPasswordField.getText().isBlank()) {
            AlertBox.display("Warning", "All * fields must be fill");
            return;
        }

        if(!passwordField.getText().equals(repeatPasswordField.getText())) {
            AlertBox.display("Warning", "Passwords don't match");
            return;
        }

        try {
            MainApplication.getDataController().register(UserDTO.builder().
                            login(loginField.getText()).
                            pass(HashPassword.getHash(passwordField.getText())).
                            name(nameField.getText()).
                            surname(surnameField.getText()).
                            build());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            return;
        }

        singupEvent.handle(new ActionEvent());
    }

    public void setSingupEvent(EventHandler<ActionEvent> singupEvent) {
        this.singupEvent = singupEvent;
    }

    public void backAction(ActionEvent actionEvent) {
        if(backEvent != null)
            backEvent.handle(new ActionEvent());
    }

    public void setBackEvent(EventHandler<ActionEvent> backEvent) {
        this.backEvent = backEvent;
    }
}
