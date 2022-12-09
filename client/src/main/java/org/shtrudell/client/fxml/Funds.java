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

    private void test() {

        var document = DocumentDTO.builder().name(DocnameDTO.builder().
                        title("Red hat").
                        edition(3).
                        isbn(16511).
                        releaseDate(LocalDate.of(2022, 12,10)).
                        author(AuthorDTO.builder().
                                name("Lox").
                                surname("Loxov").
                                patronymic("Loxovich").
                                build()).
                        build()).
                id(1).
                build();
        var document2 = DocumentDTO.builder().name(DocnameDTO.builder().
                        title("Fall of Rome").
                        edition(3).
                        isbn(15511).
                        releaseDate(LocalDate.of(2022, 12,10)).
                        author(AuthorDTO.builder().
                                name("Lox").
                                surname("Loxov").
                                patronymic("Loxovich").
                                build()).
                        build()).
                id(2).
                build();

        FundDTO fund = FundDTO.builder().documents(List.of(document,document2)).name("Fund").id(1).build();
        FundDTO fund1 = FundDTO.builder().documents(List.of(document)).name("Fund of history books").id(2).build();

        ReceiptDTO receipt = ReceiptDTO.builder().
                id(1).
                date(LocalDate.of(2022, 12, 10)).
                document(document).
                fund(fund).
                build();

        ReceiptDTO receipt1 = ReceiptDTO.builder().
                id(1).
                date(LocalDate.of(2022, 12, 10)).
                document(document2).
                fund(fund1).
                build();

        setFunds(List.of(fund, fund1), List.of(receipt, receipt1, receipt));
    }
}
