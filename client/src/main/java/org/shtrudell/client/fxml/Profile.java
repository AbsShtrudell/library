package org.shtrudell.client.fxml;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.shtrudell.client.AlertBox;
import org.shtrudell.client.MainApplication;
import org.shtrudell.common.model.UserDTO;

public class Profile {
    @FXML
    private Label loginLabel;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;

    private EventHandler<ActionEvent> closeEvent;

    @FXML
    private void initialize() {
        setUser(MainApplication.getDataController().getUser());
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
           surnameTextField.getText() ==null || surnameTextField.getText().isBlank())
            return;

        UserDTO user = MainApplication.getDataController().getUser();
        user.setName(nameTextField.getText());
        user.setSurname(surnameTextField.getText());
        UserDTO updatedUser = MainApplication.getDataController().updateUser(user);
        if(updatedUser != null) {
            AlertBox.display("Уведомление", "Профиль успешно изменен");
            setUser(updatedUser);
        }
    }

    public void setCloseEvent(EventHandler<ActionEvent> closeEvent) {
        this.closeEvent = closeEvent;
    }
}
