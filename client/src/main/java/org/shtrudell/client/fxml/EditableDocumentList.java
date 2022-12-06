package org.shtrudell.client.fxml;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import org.shtrudell.client.fxml.items.DocumentCellFactory;
import org.shtrudell.common.model.DocnameDTO;
import org.shtrudell.common.model.DocumentDTO;

import java.util.List;

public class EditableDocumentList {
    @FXML
    private ListView<DocumentDTO> documentsListView;

    @FXML
    private void initialize() {
        documentsListView.setCellFactory(new DocumentCellFactory());
        documentsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void addAction(ActionEvent actionEvent) {
        documentsListView.getItems().add(DocumentDTO.builder().build());
        documentsListView.getSelectionModel().select(documentsListView.getItems().size() - 1);
    }

    @FXML
    private void deleteAction(ActionEvent actionEvent) {
        DocumentDTO doc = documentsListView.getSelectionModel().getSelectedItem();

        if(doc == null) return;

        documentsListView.getItems().remove(doc);
    }

    public List<DocumentDTO> getItems() {
        return documentsListView.getItems();
    }

    public ReadOnlyObjectProperty<DocumentDTO> getSelectedItem() {
        return documentsListView.getSelectionModel().selectedItemProperty();
    }

    public void editSelectedItem(DocnameDTO docname) {
        if(documentsListView.getSelectionModel().getSelectedItem() == null) return;

        DocumentDTO document = documentsListView.getSelectionModel().getSelectedItem();
        int selectedIndex = documentsListView.getSelectionModel().getSelectedIndex();
        documentsListView.getItems().set(documentsListView.getSelectionModel().getSelectedIndex(), DocumentDTO.builder().
                        id(document.getId()).
                        name(docname).
                        build());
        documentsListView.refresh();
    }
}
