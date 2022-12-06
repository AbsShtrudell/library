package org.shtrudell.client.fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.shtrudell.client.fxml.items.UserCellFactory;
import org.shtrudell.common.model.FundDTO;
import org.shtrudell.common.model.RoleDTO;
import org.shtrudell.common.model.SimpleFundDTO;
import org.shtrudell.common.model.UserDTO;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class AdminUser {
    @FXML
    private ChoiceBox<RoleDTO> roleChoiceBox;
    @FXML
    private AnchorPane roleEditPane;
    @FXML
    private ListView<UserDTO> userListView;

    private List<FundDTO> funds;

    @FXML
    private void initialize() {
        userListView.setCellFactory(new UserCellFactory());

        roleChoiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldObj, newObj) -> {
            if(newObj == null) {
                clearRoleEdit();
                return;
            }

            if(userListView.getSelectionModel().getSelectedItem() != null) {
                userListView.getSelectionModel().getSelectedItem().setRole(newObj);
                createRoleEdit(newObj, funds);
            }
        });

        userListView.getSelectionModel().selectedItemProperty().addListener((v, oldObj, newObj) -> {
            if(newObj == null) return;

            for(int i = 0; i < roleChoiceBox.getItems().size(); i++) {
                if(Objects.equals(roleChoiceBox.getItems().get(i).getId(), newObj.getRole().getId())) {
                    roleChoiceBox.getSelectionModel().select(i);
                }
            }
        });

        test();
    }

    private void test() {
        FundDTO fund = FundDTO.builder().id(0).name("fund").build();
        FundDTO fund2 = FundDTO.builder().id(1).name("fund2").build();

        RoleDTO role = RoleDTO.builder().id(0).fund(new SimpleFundDTO(fund)).name("role").build();
        RoleDTO role2 = RoleDTO.builder().id(1).fund(new SimpleFundDTO(fund2)).name("role2").build();

        UserDTO user = UserDTO.builder().id(0).login("login").role(role).build();
        UserDTO user2 = UserDTO.builder().id(1).login("gg").role(role2).build();

        initData(List.of(role,role2), List.of(user, user2), List.of(fund, fund2));
    }

    public void initData(List<RoleDTO> roles, List<UserDTO> users, List<FundDTO> funds) {
        userListView.getItems().setAll(users);
        initRoleChoiceBox(roles);
        this.funds = funds;
    }

    private void initRoleChoiceBox(List<RoleDTO> roles) {
        if (roles == null) return;

        roleChoiceBox.getItems().setAll(roles);
    }

    private void clearRoleEdit() {
        roleEditPane.getChildren().clear();
    }

    private void createRoleEdit(RoleDTO role, List<FundDTO> funds) {
        FXMLLoader roleEditController = new FXMLLoader(getClass().getResource("/org/shtrudell/client/fxml/RoleEdit.fxml"));
        try {
            Pane recieptView = roleEditController.load();

            RoleEdit controller = roleEditController.getController();
            controller.init(role, funds);

            controller.getRoleProperty().addListener((v, oldObj, newObj)-> {
                if(userListView.getSelectionModel().getSelectedItem() == null) return;

                for(int i = 0; i < roleChoiceBox.getItems().size(); i++) {
                    if(Objects.equals(roleChoiceBox.getItems().get(i).getId(), newObj.getId())) {
                        roleChoiceBox.getItems().set(i, newObj);
                        roleChoiceBox.getSelectionModel().select(i);
                    }
                }
                userListView.getSelectionModel().getSelectedItem().setRole(newObj);
            });

            roleEditPane.getChildren().clear();
            roleEditPane.getChildren().add(recieptView);

        } catch (IOException ignored) {
        }
    }

    @FXML
    private void addNewRole(ActionEvent actionEvent) {
        RoleDTO role = null;

        createRoleEdit(null, funds);
    }
}

