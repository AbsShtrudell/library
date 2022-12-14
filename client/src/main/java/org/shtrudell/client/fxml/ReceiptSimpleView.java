package org.shtrudell.client.fxml;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.shtrudell.client.fxml.factory.DocumentCellFactory;
import org.shtrudell.client.util.AlertBox;
import org.shtrudell.common.model.DocumentDTO;
import org.shtrudell.common.model.ReceiptDTO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

// TODO: 12.12.2022 fix date position

public class ReceiptSimpleView {
    @FXML
    private Label userLoginLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label fundNameLAbel;
    @FXML
    private ListView<DocumentDTO> documentsListView;
    private ReceiptDTO receipt;
    @FXML
    private void initialize() {
        documentsListView.setCellFactory(new DocumentCellFactory());
    }

    public void setReceipt(ReceiptDTO receipt) {
        if(receipt == null) return;

        this.receipt = receipt;

        if(receipt.getUser() != null)
            setUserLogin(receipt.getUser().getLogin());
        setDate(receipt.getDate());
        if(receipt.getFund() != null)
            setFundName(receipt.getFund().getName());
        setDocumentsList(receipt.getDocuments());
    }

    public void setUserLogin(String userLogin) {
        if(userLogin == null) return;

        userLoginLabel.setText(userLogin);
    }

    public void setDate(LocalDate date) {
        if(date == null) return;

        dateLabel.setText(date.toString());
    }

    public void setFundName(String fundName) {
        if(fundName == null) return;

        fundNameLAbel.setText(fundName);
    }

    public void setDocumentsList(List<DocumentDTO> documents) {
        if(documents == null || documents.size() == 0) return;
        documentsListView.setItems(FXCollections.observableList(documents));
    }

    public ReadOnlyObjectProperty<DocumentDTO> getSelectedItem() {
        return documentsListView.getSelectionModel().selectedItemProperty();
    }

    public void saveToFileAction(ActionEvent actionEvent) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(String.format("%d-%s-%s.txt",receipt.getId(), receipt.getFund().getName(), receipt.getDate().toString())));
            StringBuilder receiptText = new StringBuilder(String.format("""
                    Акт о поступлении от %s
                    -------------------------------
                    Пользователь: %s
                    Фонд: %s
                    -------------------------------"""
                    , dateLabel.getText(), userLoginLabel.getText(), fundNameLAbel.getText()));
            for(var doc : documentsListView.getItems()) {
                receiptText.append("\n").append(doc.toString());
            }

            writer.write(receiptText.toString());
            writer.close();
            AlertBox.display("Уведомление", "Акт успешно сохранен в файл");
        }
        catch (IOException ignored) {

        }
    }
}
