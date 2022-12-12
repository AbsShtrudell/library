package org.shtrudell.client.net;

import org.shtrudell.common.model.*;
import org.shtrudell.common.net.AnswerMessage;
import org.shtrudell.common.net.MessageMethod;
import org.shtrudell.common.net.MessageResult;
import org.shtrudell.common.net.QueryMessage;

import java.util.List;

class NetDataController implements DataController{
    Client client;
    UserDTO user;

    public UserDTO getUser() {
        return user;
    }

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

        AnswerMessage message = client.receiveMsg();
        if(message.getResult() == MessageResult.SUCCESS) {
            this.user = message.getUsers().get(0);
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
                user(user).
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

    @Override
    public List<DocnameDTO> getAllDocnames() {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.GET_ALL_DOCNAMES).
                build());

        return client.receiveMsg().getDocnames();
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.GET_ALL_AUTHORS).
                build());

        return client.receiveMsg().getAuthors();
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.GET_ALL_ROLES).
                build());

        return client.receiveMsg().getRoles();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.GET_ALL_USERS).
                build());

        return client.receiveMsg().getUsers();
    }

    @Override
    public RoleDTO addRole(RoleDTO role) {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.ADD_ROLE).
                role(role).
                build());

        AnswerMessage message = client.receiveMsg();
        if(message.getResult().equals(MessageResult.SUCCESS))
            return message.getRoles().get(0);
        else return null;
    }

    @Override
    public FundDTO updateFund(FundDTO fund) {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.UPDATE_FUND).
                fund(fund).
                build());

        AnswerMessage message = client.receiveMsg();
        if(message.getResult().equals(MessageResult.SUCCESS))
            return message.getFunds().get(0);
        else return null;
    }

    @Override
    public RoleDTO updateRole(RoleDTO role) {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.UPDATE_ROLE).
                role(role).
                build());

        AnswerMessage message = client.receiveMsg();
        if(message.getResult().equals(MessageResult.SUCCESS))
            return message.getRoles().get(0);
        else return null;
    }

    @Override
    public FundDTO addFund(FundDTO fund) {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.ADD_FUND).
                fund(fund).
                build());

        AnswerMessage message = client.receiveMsg();
        if(message.getResult().equals(MessageResult.SUCCESS))
            return message.getFunds().get(0);
        else return null;
    }

    public void addReceipt(ReceiptDTO receipt) {
        receipt.setUser(user);
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.NEW_RECEIPT).
                receipt(receipt).
                build());
    }

    @Override
    public UserDTO updateUser(UserDTO user) {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.UPDATE_USER).
                user(user).
                build());


        AnswerMessage message = client.receiveMsg();
        if(message.getResult().equals(MessageResult.SUCCESS)) {
            if (message.getUsers().get(0).getLogin().equals(user.getLogin()))
                user = message.getUsers().get(0);

            return message.getUsers().get(0);
        }
        else return null;
    }

    public AuthorDTO addAuthor(AuthorDTO author) {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.ADD_AUTHOR).
                author(author).
                build());

        return client.receiveMsg().getAuthors().get(0);
    }

    public DocnameDTO addDocname(DocnameDTO docname) {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.ADD_DOCNAME).
                docname(docname).
                build());

        return client.receiveMsg().getDocnames().get(0);
    }
}
