package org.shtrudell.client.fxml;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.shtrudell.client.AlertBox;
import org.shtrudell.client.MainApplication;
import org.shtrudell.client.net.Client;
import org.shtrudell.common.model.*;

import java.io.IOException;
import java.util.List;

public class Receipt {
    @FXML
    private EditableDocumentList editableDocumentListController;
    @FXML
    private ChoiceBox<FundDTO> fundChoiceBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ChoiceBox<DocnameDTO> docNameChoiceBox;
    @FXML
    private AnchorPane docnamePane;

    private EventHandler<ActionEvent> closeEvent;
    private List<AuthorDTO> authors;

    @FXML
    private void initialize() {
        docNameChoiceBox.valueProperty().addListener((v, oldObj, newObj) -> {
            if(newObj == null) {
                docnamePane.getChildren().clear();
                return;
            }
            else
                createDocView(newObj);

            editableDocumentListController.editSelectedItem(newObj);
        });

        editableDocumentListController.getSelectedItem().addListener((v, oldObj, newObj) -> {
        });

        authors = MainApplication.getDataController().getAllAuthors();
        setDocnames(MainApplication.getDataController().getAllDocnames());
        setFunds(MainApplication.getDataController().getAllFunds());
    }

    public void setDocnames(List<DocnameDTO> docnames) {
        if(docnames == null) return;

        docNameChoiceBox.getItems().clear();
        docNameChoiceBox.getItems().add(null);
        docNameChoiceBox.getItems().addAll(docnames);
        docNameChoiceBox.getSelectionModel().select(0);
    }

    public void setFunds(List<FundDTO> funds) {
        if(funds == null) return;

        fundChoiceBox.getItems().clear();
        fundChoiceBox.getItems().addAll(funds);
    }

    @FXML
    private void applyAction(ActionEvent actionEvent) {
        if(fundChoiceBox.getValue() == null || editableDocumentListController.getItems().size() == 0 || datePicker.getValue() == null)
            return;

        for(var document : editableDocumentListController.getItems()) {
            if(document.getName() == null) {
                AlertBox.display("Предупреждение","Все документы должны иметь наименование");
                return;
            }
        }

        MainApplication.getDataController().addReceipt(ReceiptDTO.builder().
                date(datePicker.getValue()).
                fund(fundChoiceBox.getValue()).
                documents(editableDocumentListController.getItems()).
                build());

        closeAction(new ActionEvent());
    }

    @FXML
    private void closeAction(ActionEvent actionEvent) {
        if(closeEvent != null)
            closeEvent.handle(new ActionEvent());
    }

    public void setCloseEvent(EventHandler<ActionEvent> closeEvent) {
        this.closeEvent = closeEvent;
    }

    private void createDocView(DocnameDTO document) {
        if(document == null) return ;

        FXMLLoader docInputLoader = new FXMLLoader(getClass().getResource("/org/shtrudell/client/fxml/DocView.fxml"));
        try {
            Pane docView = docInputLoader.load();

            DocView controller = docInputLoader.getController();
            controller.setDocument(DocumentDTO.builder().name(document).build());

            docnamePane.getChildren().clear();
            docnamePane.getChildren().add(docView);

        } catch (IOException ignored) {
        }
    }

    private void createDocInput(List<AuthorDTO> authors) {
        DocnameDTO document = DocnameDTO.builder().title("New Document").build();

        FXMLLoader docInputLoader = new FXMLLoader(getClass().getResource("/org/shtrudell/client/fxml/DocInput.fxml"));
        try {
            Pane docInput = docInputLoader.load();

            DocInput controller = docInputLoader.getController();
            controller.setAuthors(authors);
            controller.setDocumentPropertyValue(document);

            controller.getDocumentProperty().addListener((v, oldVal, newVal)-> {
                if(newVal == document) return;

                DocnameDTO docname = MainApplication.getDataController().addDocname(newVal);
                if(docname != null) {
                    docNameChoiceBox.getItems().add(docname);
                    docNameChoiceBox.getSelectionModel().selectLast();
                }
            });

            docnamePane.getChildren().clear();
            docnamePane.getChildren().add(docInput);

        } catch (IOException e) {
            return;
        }
    }

    public void addNewDocNameAction(ActionEvent actionEvent) {
        docNameChoiceBox.getSelectionModel().select(0);
        createDocInput(authors);
    }
}
