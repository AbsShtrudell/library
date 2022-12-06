package org.shtrudell.client.fxml;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.shtrudell.common.model.AuthorDTO;
import org.shtrudell.common.model.DocnameDTO;
import org.shtrudell.common.model.DocumentDTO;
import org.shtrudell.common.model.FundDTO;

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

        test();
    }

    private void test() {
        authors = List.of(AuthorDTO.builder().name("dd").surname("ddov").build(), AuthorDTO.builder().name("aa").surname("aaov").build());
        setDocnames(List.of(DocnameDTO.builder().id(1).title("dfs").isbn(4515).build()));
    }

    public void setDocnames(List<DocnameDTO> docnames) {
        if(docnames == null) return;

        docNameChoiceBox.getItems().clear();
        docNameChoiceBox.getItems().add(null);
        docNameChoiceBox.getItems().addAll(docnames);
        docNameChoiceBox.getSelectionModel().select(0);
    }

    @FXML
    private void applyAction(ActionEvent actionEvent) {
    }

    @FXML
    private void closeAction(ActionEvent actionEvent) {
        if(closeEvent != null)
            closeEvent.handle(null);
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

                docNameChoiceBox.getItems().add(newVal);
                docNameChoiceBox.getSelectionModel().selectLast();
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
