package org.shtrudell.client.fxml;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.shtrudell.client.fxml.items.DocumentCellFactory;
import org.shtrudell.common.model.*;

import java.time.LocalDate;
import java.util.List;

public class FundView {
    @FXML
    private ReceiptsAccordionView receiptAccordionViewController;
    @FXML
    private DocView docViewController;
    @FXML
    private ListView<DocumentDTO> documentsListView;

    @FXML
    private void initialize() {
        documentsListView.setCellFactory(new DocumentCellFactory());
        documentsListView.getSelectionModel().selectedItemProperty().addListener((v, oldObj, newObj) -> {
            docViewController.setDocument(newObj);
        });
    }

    public void setDocuments(List<DocumentDTO> documents) {
        if(documents == null || documents.size() == 0) return;

        documentsListView.setItems(FXCollections.observableList(documents));
    }

    public void setReceipts(List<ReceiptDTO> receipts) {
        if(receipts == null || receipts.size() == 0) return;

        receiptAccordionViewController.setReceipts(receipts);
        for(var controller : receiptAccordionViewController.getSimpleViewControllers()) {
            controller.getSelectedItem().addListener((v, old, newObj) -> {docViewController.setDocument(newObj); });
        }
    }
}
