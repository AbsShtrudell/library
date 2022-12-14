package org.shtrudell.client.fxml;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.Setter;
import org.shtrudell.client.util.AlertBox;
import org.shtrudell.client.ClientApp;
import org.shtrudell.client.net.DataOperationException;
import org.shtrudell.common.util.HashPassword;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class Login {
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @Setter
    EventHandler<ActionEvent> singUpEvent;
    @Setter
    EventHandler<ActionEvent> successfulLoginEvent;

    @FXML
    private void initialize() {

    }

    @FXML
    private void loginAction(ActionEvent actionEvent) {
        if (loginField.getText() == null ||loginField.getText().isBlank() ||
            passwordField.getText() == null || passwordField.getText().isBlank()) {
            AlertBox.display("Внимание", "Все поля должны быть заполнены");
            return;
        }

        try {
            if (ClientApp.getDataController().authenticate(loginField.getText(), HashPassword.getHash(passwordField.getText())))
                successfulLoginEvent.handle(new ActionEvent());
        }
        catch (DataOperationException e) {
            AlertBox.display("Внимание", e.getMessage());
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException ignored) {
        }
    }

    @FXML
    private void singupAction(ActionEvent actionEvent) {
        if(singUpEvent != null)
            singUpEvent.handle(new ActionEvent());
    }
}
