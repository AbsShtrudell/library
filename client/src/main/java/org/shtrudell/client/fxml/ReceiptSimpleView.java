package org.shtrudell.client.fxml;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.shtrudell.client.fxml.items.DocumentCellFactory;
import org.shtrudell.common.model.DocumentDTO;
import org.shtrudell.common.model.ReceiptDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ReceiptSimpleView {
    @FXML
    private Label userLoginLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label fundNameLAbel;
    @FXML
    private ListView<DocumentDTO> documentsListView;

    @FXML
    private void initialize() {
        documentsListView.setCellFactory(new DocumentCellFactory());
    }

    public void setReceipt(ReceiptDTO receipt) {
        if(receipt == null) return;

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
}
