package org.shtrudell.client.fxml;

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

        fundNameInputField.textProperty().addListener((v, oldVal, newVal) -> {
            if(fundList.getSelectionModel().getSelectedItem() == null
                    || newVal == null
                    || newVal.isBlank()) return;

            try {
                SimpleFundDTO result = ClientApp.getDataController().updateSimpleFund(SimpleFundDTO.builder().
                        id(fundList.getSelectionModel().getSelectedItem().getId()).
                        name(newVal).
                        build());

                fundList.getSelectionModel().getSelectedItem().setName(result.getName());
            } catch (DataOperationException e) {
                AlertBox.display("Внимание", e.getMessage());
            }
        });

        try {
            fundList.getItems().setAll(ClientApp.getDataController().getAllSimpleFunds());
        } catch (DataOperationException e) {
            AlertBox.display("Внимание", e.getMessage());
        }
    }

}
