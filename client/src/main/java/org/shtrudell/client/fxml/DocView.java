package org.shtrudell.client.fxml;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.shtrudell.common.model.DocumentDTO;

import java.time.LocalDate;

public class DocView {
    @FXML
    private Label titleLabel;
    @FXML
    private Label isbnLabel;
    @FXML
    private Label editionLabel;
    @FXML
    private Label releasedDateLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label patronymicLabel;
    @FXML
    private GridPane canvas;

    @FXML
    private void initialize() {
        canvas.setVisible(false);
    }

    public void setDocument(DocumentDTO document) {
        if(document == null) return;

        canvas.setVisible(true);

        if(document.getName() != null) {
            setIsbn(document.getName().getIsbn());
            setEdition(document.getName().getEdition());
            setTitle(document.getName().getTitle());
            setReleasedDate(document.getName().getReleaseDate());

            if(document.getName().getAuthor() != null) {
                setName(document.getName().getAuthor().getName());
                setSurname(document.getName().getAuthor().getSurname());
                setPatronymic(document.getName().getAuthor().getPatronymic());
            }
        }
    }

    public void setTitle(String title) {
        if(title == null) return;
        titleLabel.setText(title);
    }

    public void setIsbn(Integer isbn) {
        if(isbn == null) return;
        isbnLabel.setText(isbn.toString());
    }

    public void setEdition(Integer edition) {
        if(edition == null) return;
        editionLabel.setText(edition.toString());
    }

    public void setReleasedDate(LocalDate date) {
        if(date == null) return;
        releasedDateLabel.setText(date.toString());
    }

    public void setName(String name) {
        if(name == null) return;
        nameLabel.setText(name);
    }

    public void setSurname(String surname) {
        if(surname == null) return;
        surnameLabel.setText(surname);
    }

    public void setPatronymic(String patronymic) {
        if(patronymic == null) return;
        patronymicLabel.setText(patronymic);
    }
}
