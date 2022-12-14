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
import org.shtrudell.common.model.UserDTO;
import org.shtrudell.common.util.HashPassword;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class Registration {
    @FXML
    private PasswordField repeatPasswordField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @Setter
    private EventHandler<ActionEvent> singupEvent;
    @Setter
    private EventHandler<ActionEvent> backEvent;

    @FXML
    private void initialize() {
    }

    public void singupAction(ActionEvent actionEvent) {
        if(loginField.getText() == null || loginField.getText().isBlank() ||
                passwordField.getText() == null || passwordField.getText().isBlank() ||
                repeatPasswordField.getText() == null || repeatPasswordField.getText().isBlank()) {
            AlertBox.display("Внимание", "Все поля с * должны быть заполнены");
            return;
        }
        if(!passwordField.getText().equals(repeatPasswordField.getText())) {
            AlertBox.display("Внимание", "Пароли не совпадают");
            return;
        }

        try {
            ClientApp.getDataController().register(UserDTO.builder().
                            login(loginField.getText()).
                            pass(HashPassword.getHash(passwordField.getText())).
                            name(nameField.getText()).
                            surname(surnameField.getText()).
                            build());
        }
        catch (DataOperationException e) {
            AlertBox.display("Внимание", e.getMessage());
            return;
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            return;
        }

        singupEvent.handle(new ActionEvent());
    }

    public void backAction(ActionEvent actionEvent) {
        if(backEvent != null)
            backEvent.handle(new ActionEvent());
    }
}
