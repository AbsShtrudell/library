package org.shtrudell.client.fxml;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.shtrudell.common.model.DocumentDTO;

import java.io.IOException;

public class MainWindow {
    @FXML
    private AnchorPane additionalWindowsPane;

    private EventHandler<ActionEvent> logOutEvent;

    @FXML
    private void initialize() {
        changeToView();
    }

    @FXML
    private void logoutAction(ActionEvent actionEvent) {
    }

    @FXML
    private void closeAction(ActionEvent actionEvent) {
    }

    @FXML
    private void newReceiptAction(ActionEvent actionEvent) {
        setAdditionalWindow(createReceiptWindow());
    }

    @FXML
    private void adminAction(ActionEvent actionEvent) {
        setAdditionalWindow(createAdminWindow());
    }

    private void setAdditionalWindow(Pane additionalWindow) {
        additionalWindowsPane.getChildren().clear();

        if(additionalWindow != null)
            additionalWindowsPane.getChildren().add(additionalWindow);
    }

    private Pane createAdminWindow() {
        FXMLLoader adminLoader = new FXMLLoader(getClass().getResource("/org/shtrudell/client/fxml/Admin.fxml"));
        try {
            Pane admin = adminLoader.load();

            Admin controller = adminLoader.getController();
            controller.setCloseEvent(e->changeToView());

            return admin;
        } catch (IOException e) {
            return null;
        }
    }

    private void changeToView() {
        setAdditionalWindow(createViewWindow());
    }

    private Pane createReceiptWindow() {
        FXMLLoader receiptLoader = new FXMLLoader(getClass().getResource("/org/shtrudell/client/fxml/Receipt.fxml"));
        try {
            Pane receipt = receiptLoader.load();

            Receipt controller = receiptLoader.getController();
            controller.setCloseEvent(e->changeToView());

            return receipt;
        } catch (IOException e) {
            return null;
        }
    }

    private Pane createViewWindow() {
        FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/org/shtrudell/client/fxml/Funds.fxml"));
        try {
            Pane view = viewLoader.load();

            Funds controller = viewLoader.getController();

            return view;
        } catch (IOException e) {
            return null;
        }
    }

    public void setLogOutEvent(EventHandler<ActionEvent> logOutEvent) {
        this.logOutEvent = logOutEvent;
    }
}
