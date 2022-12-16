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

    public NetDataController(Client client) {
        this.client = client;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public UserDTO getUser() {
        return user;
    }

    @Override
    public boolean authenticate(String login, byte[] password) throws DataOperationException {
        client.sendMsg(QueryMessage.builder().
                        method(MessageMethod.AUTHENTICATE).
                        user(UserDTO.builder().
                                login(login).
                                pass(password).
                                build()).
                        build());

        AnswerMessage message = client.receiveMsg();

        checkMessage(message);

        setUser(message.getUsers().get(0));
        return true;
    }

    @Override
    public void register(UserDTO user) throws DataOperationException{
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.REGISTER).
                user(user).
                build());

        AnswerMessage message = client.receiveMsg();
        checkMessage(message);
    }

    @Override
    public RoleDTO addRole(RoleDTO role) throws DataOperationException {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.ADD_ROLE).
                role(role).
                build());

        AnswerMessage message = client.receiveMsg();
        checkMessage(message);

        return message.getRoles().get(0);
    }

    @Override
    public AuthorDTO addAuthor(AuthorDTO author) throws DataOperationException {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.ADD_AUTHOR).
                author(author).
                build());

        AnswerMessage message = client.receiveMsg();
        checkMessage(message);

        return message.getAuthors().get(0);
    }

    @Override
    public DocnameDTO addDocname(DocnameDTO docname) throws DataOperationException {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.ADD_DOCNAME).
                docname(docname).
                build());

        AnswerMessage message = client.receiveMsg();
        checkMessage(message);

        return message.getDocnames().get(0);
    }

    @Override
    public void addReceipt(ReceiptDTO receipt) throws DataOperationException {
        receipt.setUser(user);
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.ADD_RECEIPT).
                receipt(receipt).
                build());

        AnswerMessage message = client.receiveMsg();
        checkMessage(message);
    }

    @Override
    public RoleDTO updateRole(RoleDTO role) throws DataOperationException {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.UPDATE_ROLE).
                role(role).
                build());

        AnswerMessage message = client.receiveMsg();
        checkMessage(message);

        return message.getRoles().get(0);
    }

    @Override
    public SimpleFundDTO updateSimpleFund(SimpleFundDTO fund) throws DataOperationException {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.UPDATE_SIMPLE_FUND).
                simpleFund(fund).
                build());

        AnswerMessage message = client.receiveMsg();
        checkMessage(message);

        return message.getSimpleFunds().get(0);
    }

    @Override
    public UserDTO updateUser(UserDTO user) throws DataOperationException {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.UPDATE_USER).
                user(user).
                build());


        AnswerMessage message = client.receiveMsg();
        checkMessage(message);

        if (message.getUsers().get(0).getLogin().equals(user.getLogin()))
            setUser(message.getUsers().get(0));

        return message.getUsers().get(0);
    }

    @Override
    public List<FundDTO> getAllFunds() throws DataOperationException {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.GET_ALL_FUNDS).
                user(user).
                build());

        AnswerMessage message = client.receiveMsg();
        checkMessage(message);

        return message.getFunds();
    }

    public List<SimpleFundDTO> getAllSimpleFunds() throws DataOperationException {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.GET_ALL_SIMPLE_FUNDS).
                user(user).
                build());

        AnswerMessage message = client.receiveMsg();
        checkMessage(message);

        return message.getSimpleFunds();
    }

    @Override
    public List<ReceiptDTO> getAllReceiptsOfFunds(List<FundDTO> funds) throws DataOperationException {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.GET_ALL_RECEIPTS_OF_FUNDS).
                funds(funds).
                build());

        AnswerMessage message = client.receiveMsg();
        checkMessage(message);

        return message.getReceipts();
    }

    @Override
    public List<DocnameDTO> getAllDocnames() throws DataOperationException {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.GET_ALL_DOCNAMES).
                build());

        AnswerMessage message = client.receiveMsg();
        checkMessage(message);

        return message.getDocnames();
    }

    @Override
    public List<AuthorDTO> getAllAuthors() throws DataOperationException {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.GET_ALL_AUTHORS).
                build());

        AnswerMessage message = client.receiveMsg();
        checkMessage(message);

        return message.getAuthors();
    }

    @Override
    public List<RoleDTO> getAllRoles() throws DataOperationException {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.GET_ALL_ROLES).
                build());

        AnswerMessage message = client.receiveMsg();
        checkMessage(message);

        return message.getRoles();
    }

    @Override
    public List<UserDTO> getAllUsers() throws DataOperationException {
        client.sendMsg(QueryMessage.builder().
                method(MessageMethod.GET_ALL_USERS).
                build());

        AnswerMessage message = client.receiveMsg();
        checkMessage(message);

        return message.getUsers();
    }

    private void checkMessage(AnswerMessage message) throws DataOperationException {
        if(message.getResult() == MessageResult.FAILURE)
            throw new DataOperationException(message.getMessage());
    }
}
