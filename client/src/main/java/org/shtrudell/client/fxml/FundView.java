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

        test();
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

    private void test() {
        var document = DocumentDTO.builder().name(DocnameDTO.builder().
                        title("Red hat").
                        edition(3).
                        isbn(16511).
                        releaseDate(LocalDate.of(2022, 12,10)).
                        author(AuthorDTO.builder().
                                name("Lox").
                                surname("Loxov").
                                patronymic("Loxovich").
                                build()).
                        build()).
                id(1).
                build();
        var document2 = DocumentDTO.builder().name(DocnameDTO.builder().
                        title("Fall of Rome").
                        edition(3).
                        isbn(15511).
                        releaseDate(LocalDate.of(2022, 12,10)).
                        author(AuthorDTO.builder().
                                name("Lox").
                                surname("Loxov").
                                patronymic("Loxovich").
                                build()).
                        build()).
                id(2).
                build();

        setDocuments(List.of(document, document2));

        ReceiptDTO receipt = ReceiptDTO.builder().
                id(1).
                date(LocalDate.of(2022, 12, 10)).
                fund(FundDTO.builder().
                        id(1).name("Fund of books").
                        build()).
                document(document).
                build();

        setReceipts(List.of(receipt,receipt,receipt,receipt,receipt,receipt,receipt,receipt,receipt,receipt,receipt,receipt));
    }
}
