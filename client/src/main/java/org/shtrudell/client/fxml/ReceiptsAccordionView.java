package org.shtrudell.client.fxml;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import lombok.Getter;
import org.shtrudell.client.util.FXMLHelper;
import org.shtrudell.common.model.ReceiptDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ReceiptsAccordionView {
    @FXML
    private Accordion canvas;
    @Getter
    private final List<ReceiptSimpleView> simpleViewControllers;

    @FXML
    private void initialize() {

    }

    public ReceiptsAccordionView() {
        simpleViewControllers = new ArrayList<>();
    }

    public void setReceipts(List<ReceiptDTO> receipts) {
        if(receipts == null || receipts.size() == 0) return;

        List<TitledPane> titledPanes = new ArrayList<>();
        for(var receipt : receipts) {
            var pane = createPane(receipt);
            if(pane == null) continue;
            titledPanes.add(pane);
        }

        canvas.getPanes().clear();

        if(titledPanes.size() != 0)
            canvas.getPanes().addAll(titledPanes);
    }

    private TitledPane createPane(ReceiptDTO receipt) {
        if(receipt == null) return null;

        TitledPane pane = new TitledPane();

        pane.setText(receipt.toString());
        pane.setMinHeight(200);
        pane.setMaxHeight(300);

        FXMLLoader recieptViewLoader = FXMLHelper.makeLoader("ReceiptSimpleView");
        try {
            Pane recieptView = recieptViewLoader.load();

            ReceiptSimpleView controller = recieptViewLoader.getController();
            controller.setReceipt(receipt);
            simpleViewControllers.add(controller);

            pane.setContent(recieptView);
            return pane;
        } catch (IOException e) {
            return null;
        }
    }
}
