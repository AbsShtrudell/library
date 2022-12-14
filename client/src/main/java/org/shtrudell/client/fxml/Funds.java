package org.shtrudell.client.fxml;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import org.shtrudell.client.ClientApp;
import org.shtrudell.client.net.DataOperationException;
import org.shtrudell.client.util.AlertBox;
import org.shtrudell.client.util.FXMLHelper;
import org.shtrudell.common.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Funds {
    @FXML
    public TabPane fundsTabPane;

    @FXML
    private void initialize() {
        try {
            List<FundDTO> funds = ClientApp.getDataController().getAllFunds();
            List<ReceiptDTO> receipts = ClientApp.getDataController().getAllReceiptsOfFunds(funds);
            setFunds(funds, receipts);
        }
        catch (DataOperationException e) {
            AlertBox.display("Внимание", e.getMessage());
        }
    }

    public void setFunds(List<FundDTO> funds, List<ReceiptDTO> receipts) {
        if (funds == null || funds.size() == 0) return;

        for (var fund : funds) {
            List<ReceiptDTO> receiptList = new ArrayList<>();
            for (var receipt : receipts) {
                if (receipt.getFund().getId().equals(fund.getId()))
                    receiptList.add(receipt);
            }
            var tab = createTab(fund, receiptList);

            if(tab == null) continue;

            fundsTabPane.getTabs().add(tab);
        }
    }

    private Tab createTab(FundDTO fund, List<ReceiptDTO> receipts) {
        if(fund == null) return null;
        Tab tab = new Tab(fund.getName());

        FXMLLoader fundViewLoader = FXMLHelper.makeLoader("FundView");
        try {
            Pane fundView = fundViewLoader.load();

            FundView controller = fundViewLoader.getController();
            controller.setReceipts(receipts);
            controller.setDocuments(fund.getDocuments());

            tab.setContent(fundView);

            return tab;
        } catch (IOException e) {
            return null;
        }
    }
}
