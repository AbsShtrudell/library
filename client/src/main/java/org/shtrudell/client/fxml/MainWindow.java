package org.shtrudell.client.fxml;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lombok.Setter;
import org.shtrudell.client.ClientApp;
import org.shtrudell.client.util.FXMLHelper;

import java.io.IOException;

public class MainWindow {
    @FXML
    private MenuItem adminMenuItem;
    @FXML
    private AnchorPane additionalWindowsPane;
    @Setter
    private EventHandler<ActionEvent> logOutEvent;
    @Setter
    private EventHandler<ActionEvent> closeEvent;

    // TODO: 12.12.2022 fix available admin panel after changing role

    // TODO: 12.12.2022 fix admin role check
    @FXML
    private void initialize() {
        changeToView();
        adminMenuItem.setDisable(!ClientApp.getDataController().getUser().getRole().getName().equals("Admin"));
    }

    @FXML
    private void logoutAction(ActionEvent actionEvent) {
        if(logOutEvent != null)
            logOutEvent.handle(new ActionEvent());
    }

    @FXML
    private void closeAction(ActionEvent actionEvent) {
        if(closeEvent != null)
            closeEvent.handle(new ActionEvent());
    }

    @FXML
    private void newReceiptAction(ActionEvent actionEvent) {
        changeToNewReceipt();
    }

    @FXML
    private void adminAction(ActionEvent actionEvent) {
        changeToAdmin();
    }

    @FXML
    private void profileAction(ActionEvent actionEvent) {
        changeToProfile();
    }

    private Pane createAdminWindow() {
        FXMLLoader adminLoader = FXMLHelper.makeLoader("Admin");
        try {
            Pane admin = adminLoader.load();

            Admin controller = adminLoader.getController();
            controller.setCloseEvent(e->changeToView());

            return admin;
        } catch (IOException e) {
            return null;
        }
    }

    private Pane createReceiptWindow() {
        FXMLLoader receiptLoader = FXMLHelper.makeLoader("Receipt");
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
        FXMLLoader viewLoader = FXMLHelper.makeLoader("Funds");
        try {
            return viewLoader.load();
        } catch (IOException e) {
            return null;
        }
    }

    private Pane createProfileWindow() {
        FXMLLoader profileLoader = FXMLHelper.makeLoader("Profile");
        try {
            Pane view = profileLoader.load();

            Profile controller = profileLoader.getController();

            controller.setCloseEvent(e-> {changeToView();});

            return view;
        } catch (IOException e) {
            return null;
        }
    }

    private void setAdditionalWindow(Pane additionalWindow) {
        additionalWindowsPane.getChildren().clear();

        if(additionalWindow != null)
            additionalWindowsPane.getChildren().add(additionalWindow);
    }

    private void changeToView() {
        setAdditionalWindow(createViewWindow());
    }

    private void changeToAdmin() {
        setAdditionalWindow(createAdminWindow());
    }

    private void changeToProfile() {
        setAdditionalWindow(createProfileWindow());
    }

    private void changeToNewReceipt() {
        setAdditionalWindow(createReceiptWindow());
    }
}
