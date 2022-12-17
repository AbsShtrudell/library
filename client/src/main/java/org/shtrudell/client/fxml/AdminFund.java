package org.shtrudell.client.fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.shtrudell.client.ClientApp;
import org.shtrudell.client.fxml.factory.SimpleFundCellFactory;
import org.shtrudell.client.net.DataOperationException;
import org.shtrudell.client.util.AlertBox;
import org.shtrudell.common.model.SimpleFundDTO;

public class AdminFund {
    @FXML
    private TextField fundNameInputField;
    @FXML
    private ListView<SimpleFundDTO> fundList;

    @FXML
    private void initialize() {
        fundList.setCellFactory(new SimpleFundCellFactory());

        fundList.getSelectionModel().selectedItemProperty().addListener((v, oldVal, newVal) -> {
            if(newVal == null) return;

            fundNameInputField.setText(newVal.getName());
        });

        try {
            fundList.getItems().setAll(ClientApp.getDataController().getAllSimpleFunds());
        } catch (DataOperationException e) {
            AlertBox.display("Внимание", e.getMessage());
        }
    }

    public void saveAction(ActionEvent actionEvent) {
        if(fundNameInputField.getText() == null || fundNameInputField.getText().isBlank()) {
            AlertBox.display("Внимание", "Название не должно быть пустым");
            return;
        }

        try {
            SimpleFundDTO result = ClientApp.getDataController().updateSimpleFund(SimpleFundDTO.builder().
                    id(fundList.getSelectionModel().getSelectedItem().getId()).
                    name(fundNameInputField.getText()).
                    build());

            fundList.getSelectionModel().getSelectedItem().setName(fundNameInputField.getText());
        } catch (DataOperationException e) {
            AlertBox.display("Внимание", e.getMessage());
        }
    }
}
