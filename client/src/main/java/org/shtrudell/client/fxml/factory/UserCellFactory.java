package org.shtrudell.client.fxml.factory;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.shtrudell.common.model.UserDTO;


public class UserCellFactory implements Callback<ListView<UserDTO>, ListCell<UserDTO>> {
    @Override
    public ListCell<UserDTO> call(ListView<UserDTO> param) {
        return new ListCell<UserDTO>(){
            @Override
            public void updateItem(UserDTO user, boolean empty) {
                super.updateItem(user, empty);
                if (empty || user == null) {
                    setText(null);
                } else {
                    String login = user.getLogin() == null? "Unknown User" : user.getLogin();
                    if(user.getId() != null)
                        setText(user.getId() + ": " + login);
                    else
                        setText("new: " + login);
                }
            }
        };
    }
}
