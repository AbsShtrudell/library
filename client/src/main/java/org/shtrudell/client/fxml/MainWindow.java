package org.shtrudell.client.fxml;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.shtrudell.client.MainApplication;
import org.shtrudell.client.net.DataController;
import org.shtrudell.common.model.DocumentDTO;

import java.io.IOException;

public class MainWindow {
    @FXML
    private MenuItem adminMenuItem;
    @FXML
    private AnchorPane additionalWindowsPane;

    private EventHandler<ActionEvent> logOutEvent;
    private EventHandler<ActionEvent> closeEvent;

    @FXML
    private void initialize() {
        changeToView();
        if(MainApplication.getDataController().getUser().getRole().getName().equals("Admin")) {
            adminMenuItem.setDisable(false);
        }
        else adminMenuItem.setDisable(true);
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

    private Pane createProfileWindow() {
        FXMLLoader profileLoader = new FXMLLoader(getClass().getResource("/org/shtrudell/client/fxml/Profile.fxml"));
        try {
            Pane view = profileLoader.load();

            Profile controller = profileLoader.getController();

            controller.setCloseEvent(e-> {changeToView();});

            return view;
        } catch (IOException e) {
            return null;
        }
    }

    public void setLogOutEvent(EventHandler<ActionEvent> logOutEvent) {
        this.logOutEvent = logOutEvent;
    }

    public void setCloseEvent(EventHandler<ActionEvent> closeEvent) {
        this.closeEvent = closeEvent;
    }

    public void profileAction(ActionEvent actionEvent) {
        setAdditionalWindow(createProfileWindow());
    }
}
