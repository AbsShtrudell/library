package org.shtrudell.client.fxml.items;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.shtrudell.common.model.DocumentDTO;


public class DocumentCellFactory implements Callback<ListView<DocumentDTO>, ListCell<DocumentDTO>> {
    @Override
    public ListCell<DocumentDTO> call(ListView<DocumentDTO> param) {
        return new ListCell<DocumentDTO>(){
            @Override
            public void updateItem(DocumentDTO document, boolean empty) {
                super.updateItem(document, empty);
                if (empty || document == null) {
                    setText(null);
                } else {
                    String title = document.getName() == null? "Unknown Document" : document.getName().getTitle();
                    if(document.getId() != null)
                        setText(document.getId() + ": " + title);
                    else
                        setText("new: " + title);
                }
            }
        };
    }
}
