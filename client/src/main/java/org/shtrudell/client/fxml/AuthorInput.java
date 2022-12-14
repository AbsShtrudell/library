package org.shtrudell.client.fxml;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.Getter;
import org.shtrudell.client.util.AlertBox;
import org.shtrudell.common.model.AuthorDTO;

public class AuthorInput {
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField patronymicTextField;
    @FXML
    private TextField surnameTextField;
    @Getter
    private final ObjectProperty<AuthorDTO> authorProperty;

    public AuthorInput() {
        authorProperty = new SimpleObjectProperty<>();
    }

    @FXML
    private void saveAction(ActionEvent actionEvent) {
        if(nameTextField.getText() == null || nameTextField.getText().isBlank() ||
            patronymicTextField.getText() == null || patronymicTextField.getText().isBlank() ||
            surnameTextField.getText() == null || surnameTextField.getText().isBlank()) {
            AlertBox.display("Внимание", "Все поля должны быть заполнены");
            return;
        }

        authorProperty.setValue(AuthorDTO.builder().
                name(nameTextField.getText()).
                surname(surnameTextField.getText()).
                patronymic(patronymicTextField.getText()).
                build());
    }
}
