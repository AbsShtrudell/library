package org.shtrudell.client.fxml;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import org.shtrudell.client.MainApplication;
import org.shtrudell.common.model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Funds {
    @FXML
    public TabPane fundsTabPane;

    @FXML
    private void initialize() {
        List<FundDTO> funds = MainApplication.getDataController().getAllFunds();
        setFunds(funds, MainApplication.getDataController().getAllReceiptsOfFunds(funds));
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

        FXMLLoader fundViewLoader = new FXMLLoader(getClass().getResource("/org/shtrudell/client/fxml/FundView.fxml"));
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
