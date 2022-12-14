package org.shtrudell.client.fxml;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.Getter;
import org.controlsfx.control.CheckComboBox;
import org.shtrudell.client.ClientApp;
import org.shtrudell.client.net.DataOperationException;
import org.shtrudell.client.util.AlertBox;
import org.shtrudell.common.model.FundDTO;
import org.shtrudell.common.model.RoleDTO;
import org.shtrudell.common.model.SimpleFundDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// TODO: 12.12.2022 role name in choice box doesnt update after role update 

public class RoleEdit {
    @FXML
    private TextField nameTextField;
    @FXML
    private CheckComboBox<FundDTO> fundChoiceBox;
    @Getter
    private final ObjectProperty<RoleDTO> roleProperty;

    public RoleEdit() {
        roleProperty = new SimpleObjectProperty<>();
    }

    @FXML
    private void initialize() {

    }

    public void init(RoleDTO role, List<FundDTO> funds) {
        if(role == null) role = RoleDTO.builder().
                name("New Role").
                build();

        setRolePropertyValue(role);
        setFunds(funds);

        nameTextField.setText(role.getName());

        initChoiceBox(role);
    }

    private void initChoiceBox(RoleDTO role) {
        for (int i = 0; i < fundChoiceBox.getItems().size(); i++) {
            for (int j = 0; j < role.getFunds().size(); j++) {
                if (Objects.equals(fundChoiceBox.getItems().get(i).getId(), role.getFunds().get(j).getId())) {
                    fundChoiceBox.getCheckModel().checkIndices(i);
                    break;
                }
            }
        }
    }

    private void setRolePropertyValue(RoleDTO role) {
        roleProperty.setValue(role);
    }

    private void setFunds(List<FundDTO> funds) {
        if(funds == null) return;
        fundChoiceBox.getItems().addAll(funds);
    }

    private List<SimpleFundDTO> getSelectedFunds() {
        List<SimpleFundDTO> simpleFunds = new ArrayList<>();

        for(var fund : fundChoiceBox.getCheckModel().getCheckedItems().stream().toList()) {
            simpleFunds.add(new SimpleFundDTO(fund));
        }

        return simpleFunds;
    }

    public void saveAction(ActionEvent actionEvent) {
        if(nameTextField.getText() == null || nameTextField.getText().isBlank()) {
            AlertBox.display("Внимание", "Название не может быть пустым");
            return;
        }

        RoleDTO editedRole = RoleDTO.builder().
                id(roleProperty.get().getId()).
                name(nameTextField.getText()).
                funds(getSelectedFunds()).
                build();

        try {
            if (editedRole.getId() == null) {
                editedRole = ClientApp.getDataController().addRole(editedRole);
                AlertBox.display("Уведомление", "Роль успешно создана");
            } else {
                editedRole = ClientApp.getDataController().updateRole(editedRole);
                AlertBox.display("Уведомление", "Роль успешно обновлена");
            }
        }
        catch (DataOperationException e) {
            AlertBox.display("Внимание", e.getMessage());
            return;
        }
        setRolePropertyValue(editedRole);
    }
}
