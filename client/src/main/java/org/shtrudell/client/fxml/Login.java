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
import org.shtrudell.common.util.HashPassword;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class Login {
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    EventHandler<ActionEvent> singUpEvent;

    EventHandler<ActionEvent> successfulLoginEvent;

    public void setSuccessfulLoginEvent(EventHandler<ActionEvent> successfulLoginEvent) {
        this.successfulLoginEvent = successfulLoginEvent;
    }

    public void setSingUpEvent(EventHandler<ActionEvent> singUpEvent) {
        this.singUpEvent = singUpEvent;
    }


    @FXML
    private void initialize() {

    }

    @FXML
    private void loginAction(ActionEvent actionEvent) {
        if (loginField.getText() == null || loginField.getText().isBlank()) {
            AlertBox.display("Warning", "Enter login");
            return;
        }

        if (passwordField.getText() == null || passwordField.getText().isBlank()) {
            AlertBox.display("Warning", "Enter password");
            return;
        }

        try {
            if (MainApplication.getDataController().authenticate(loginField.getText(), HashPassword.getHash(passwordField.getText())))
                successfulLoginEvent.handle(new ActionEvent());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void singupAction(ActionEvent actionEvent) {
        if(singUpEvent != null)
            singUpEvent.handle(new ActionEvent());
    }
}
