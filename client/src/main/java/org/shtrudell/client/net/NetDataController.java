package org.shtrudell.client.net;

import org.shtrudell.common.model.FundDTO;
import org.shtrudell.common.model.ReceiptDTO;
import org.shtrudell.common.model.UserDTO;
import org.shtrudell.common.net.MessageMethod;
import org.shtrudell.common.net.MessageResult;
import org.shtrudell.common.net.QueryMessage;

import java.util.List;

class NetDataController implements DataController{
    Client client;
    String login;

    public NetDataController(Client client) {
        this.client = client;
    }

    public boolean authenticate(String login, byte[] password) {
        client.sendMsg(QueryMessage.builder().
                        method(MessageMethod.AUTHENTICATE).
                        user(UserDTO.builder().
                                login(login).
                                pass(password).
                                build()).
                        build());


        if(client.receiveMsg().getResult() == MessageResult.SUCCESS) {
            this.login = login;
            return true;
        } else return false;
    }

    public void register(UserDTO user) {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.REGISTER).
                user(user).
                build());

        client.receiveMsg();
    }

    public List<FundDTO> getAllFunds() {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.GET_ALL_FUNDS).
                user(UserDTO.builder().
                        login(login).
                        build()).
                build());

        return client.receiveMsg().getFunds();
    }

    @Override
    public List<ReceiptDTO> getAllReceiptsOfFunds(List<FundDTO> funds) {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.GET_ALL_RECEIPTS_OF_FUNDS).
                funds(funds).
                build());

        return client.receiveMsg().getReceipts();
    }
}
