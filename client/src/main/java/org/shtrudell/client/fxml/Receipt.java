package org.shtrudell.client.fxml;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lombok.Setter;
import org.shtrudell.client.net.DataOperationException;
import org.shtrudell.client.util.AlertBox;
import org.shtrudell.client.ClientApp;
import org.shtrudell.client.util.FXMLHelper;
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
    @Setter
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

        authors = ClientApp.getDataController().getAllAuthors();
        setDocnames(ClientApp.getDataController().getAllDocnames());
        setFunds(ClientApp.getDataController().getAllFunds());
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
        if(fundChoiceBox.getValue() == null || datePicker.getValue() == null) {
            AlertBox.display("Внимание", "Все поля должны быть заполнены");
            return;
        }

        if(editableDocumentListController.getItems().size() == 0) {
            AlertBox.display("Внимание", "Должен быть хотя бы один документ");
            return;
        }

        for(var document : editableDocumentListController.getItems()) {
            if(document.getName() == null || document.getName().getId() == null) {
                AlertBox.display("Предупреждение","Все документы должны иметь наименование");
                return;
            }
        }

        try {
            ClientApp.getDataController().addReceipt(ReceiptDTO.builder().
                    date(datePicker.getValue()).
                    fund(fundChoiceBox.getValue()).
                    documents(editableDocumentListController.getItems()).
                    build());

            AlertBox.display("Уведомление", "Акт успешно создан");

            closeAction(new ActionEvent());
        }
        catch (DataOperationException e) {
            AlertBox.display("Внимание", e.getMessage());
        }
    }

    @FXML
    private void closeAction(ActionEvent actionEvent) {
        if(closeEvent != null)
            closeEvent.handle(new ActionEvent());
    }

    private void createDocView(DocnameDTO document) {
        if(document == null) return ;

        FXMLLoader docInputLoader = FXMLHelper.makeLoader("DocView");
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

        FXMLLoader docInputLoader = FXMLHelper.makeLoader("DocInput");
        try {
            Pane docInput = docInputLoader.load();

            DocInput controller = docInputLoader.getController();
            controller.setAuthors(authors);
            controller.setDocumentPropertyValue(document);

            controller.getDocumentProperty().addListener((v, oldVal, newVal)-> {
                if(newVal == document) return;

                DocnameDTO docname = ClientApp.getDataController().addDocname(newVal);
                if(docname != null) {
                    docNameChoiceBox.getItems().add(docname);
                    docNameChoiceBox.getSelectionModel().selectLast();
                }
            });

            docnamePane.getChildren().clear();
            docnamePane.getChildren().add(docInput);

        } catch (IOException ignored) {
        }
    }

    public void addNewDocNameAction(ActionEvent actionEvent) {
        docNameChoiceBox.getSelectionModel().select(0);
        createDocInput(authors);
    }
}
