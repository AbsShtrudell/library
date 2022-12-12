package org.shtrudell.client.fxml;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.shtrudell.client.MainApplication;
import org.shtrudell.common.model.AuthorDTO;
import org.shtrudell.common.model.DocnameDTO;

import java.io.IOException;
import java.util.List;

public class DocInput {
    @FXML
    private AnchorPane authorInputPane;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField isbnTextField;
    @FXML
    private TextField editionTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ChoiceBox<AuthorDTO> authorChoiceBox;

    private final ObjectProperty<DocnameDTO> documentProperty;

    public DocInput() {
        documentProperty = new SimpleObjectProperty<DocnameDTO>();
    }

    @FXML
    private void initialize() {
    }

    public void setAuthors(List<AuthorDTO> authors) {
        if(authors == null) return;

        authorChoiceBox.getItems().addAll(authors);
    }

    public void setDocumentPropertyValue(DocnameDTO documentProperty) {
        this.documentProperty.setValue(documentProperty);
    }

    public ReadOnlyObjectProperty<DocnameDTO> getDocumentProperty() {
        return documentProperty;
    }

    public void saveAction(ActionEvent actionEvent) {
        if (isbnTextField.getText() == null || editionTextField.getText() == null) return;

        int edition;
        int isbn;

        try {
            edition = Integer.parseInt(editionTextField.getText());
            isbn = Integer.parseInt(isbnTextField.getText());
        } catch (NumberFormatException e) {
            return;
        }

        documentProperty.setValue(DocnameDTO.builder().
                title(titleTextField.getText() == null? "": titleTextField.getText()).
                releaseDate(datePicker.getValue() == null? null: datePicker.getValue()).
                edition(edition).
                author(authorChoiceBox.getValue()).
                isbn(isbn).
                build());
    }

    @FXML
    private void addNewAuthorAction(ActionEvent actionEvent) {
        createAuthorInput();
    }

    private void createAuthorInput() {

        FXMLLoader authorInputLoader = new FXMLLoader(getClass().getResource("/org/shtrudell/client/fxml/AuthorInput.fxml"));
        try {
            Pane authorInput = authorInputLoader.load();

            AuthorInput controller = authorInputLoader.getController();
            controller.getAuthorProperty().addListener((v, oldObj, newObj) -> {
                AuthorDTO author = MainApplication.getDataController().addAuthor(newObj);

                if(author != null) {
                    authorChoiceBox.getItems().add(author);
                    authorChoiceBox.getSelectionModel().selectLast();
                    authorInputPane.getChildren().clear();
                }
            });

            authorInputPane.getChildren().clear();
            authorInputPane.getChildren().add(authorInput);

        } catch (IOException e) {
            return;
        }
    }
}