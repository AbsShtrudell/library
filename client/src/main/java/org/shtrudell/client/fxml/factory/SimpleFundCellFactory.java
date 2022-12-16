package org.shtrudell.client.fxml.factory;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.shtrudell.common.model.SimpleFundDTO;


public class SimpleFundCellFactory implements Callback<ListView<SimpleFundDTO>, ListCell<SimpleFundDTO>> {
    @Override
    public ListCell<SimpleFundDTO> call(ListView<SimpleFundDTO> param) {
        return new ListCell<SimpleFundDTO>(){
            @Override
            public void updateItem(SimpleFundDTO fund, boolean empty) {
                super.updateItem(fund, empty);
                if (empty || fund == null) {
                    setText(null);
                } else {
                    if(fund.getId() != null)
                        setText(fund.getId() + ": " + fund.getName());
                    else
                        setText("new: " + fund.getName());
                }
            }
        };
    }
}
