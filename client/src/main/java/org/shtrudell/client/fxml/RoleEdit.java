package org.shtrudell.client.fxml;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;
import org.shtrudell.common.model.FundDTO;
import org.shtrudell.common.model.RoleDTO;
import org.shtrudell.common.model.SimpleFundDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoleEdit {
    @FXML
    private TextField nameTextField;
    @FXML
    private CheckComboBox<FundDTO> fundChoiceBox;

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

    public ReadOnlyObjectProperty<RoleDTO> getRoleProperty() {
        return roleProperty;
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
        RoleDTO editedRole = RoleDTO.builder().
                id(roleProperty.get().getId()).
                name(nameTextField.getText()).
                funds(getSelectedFunds()).
                build();

        setRolePropertyValue(editedRole);
    }
}
