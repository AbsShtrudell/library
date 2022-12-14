package org.shtrudell.client.fxml;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Setter;
import org.shtrudell.client.net.DataOperationException;
import org.shtrudell.client.util.AlertBox;
import org.shtrudell.client.ClientApp;
import org.shtrudell.common.model.UserDTO;

// TODO: 13.12.2022 fix update user info

public class Profile {
    @FXML
    private Label loginLabel;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @Setter
    private EventHandler<ActionEvent> closeEvent;

    @FXML
    private void initialize() {
        setUser(ClientApp.getDataController().getUser());
    }

    private void setUser(UserDTO user) {
        if(user == null) return;

        loginLabel.setText('@' + user.getLogin());
        nameTextField.setText(user.getName());
        surnameTextField.setText(user.getSurname());
    }

    @FXML
    private void closeAction(ActionEvent actionEvent) {
        if(closeEvent != null)
            closeEvent.handle(new ActionEvent());
    }

    @FXML
    private void applyAction(ActionEvent actionEvent) {
        if(nameTextField.getText() == null || nameTextField.getText().isBlank() ||
           surnameTextField.getText() ==null || surnameTextField.getText().isBlank()) {
            AlertBox.display("Внимание", "Все поля должны быть заполнены");
            return;
        }

        UserDTO user = ClientApp.getDataController().getUser();
        user.setName(nameTextField.getText());
        user.setSurname(surnameTextField.getText());

        try {
            UserDTO updatedUser = ClientApp.getDataController().updateUser(user);
            if (updatedUser != null) {
                AlertBox.display("Уведомление", "Профиль успешно изменен");
                setUser(updatedUser);
            }
        }
        catch (DataOperationException e) {
            AlertBox.display("Внимание", e.getMessage());
        }
    }
}
