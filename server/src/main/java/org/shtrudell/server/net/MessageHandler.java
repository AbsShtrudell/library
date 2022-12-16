package org.shtrudell.server.net;

import jakarta.persistence.PersistenceException;
import org.shtrudell.common.model.*;
import org.shtrudell.common.net.AnswerMessage;
import org.shtrudell.common.net.MessageResult;
import org.shtrudell.common.net.QueryMessage;
import org.shtrudell.server.controller.UserController;
import org.shtrudell.server.integration.StandardDao;
import org.shtrudell.server.integration.StandardDaoImp;
import org.shtrudell.server.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MessageHandler {
    UserController userController;
    StandardDao<Receipt> receiptDao;
    StandardDao<Fund> fundDao;
    StandardDao<Author> authorDao;
    StandardDao<Docname> docnameDao;
    StandardDao<Document> documentDao;
    StandardDao<Role> roleDao;

    public MessageHandler() {
        userController = new UserController();
        receiptDao = new StandardDaoImp<>(Receipt.class);
        fundDao = new StandardDaoImp<>(Fund.class);
        authorDao = new StandardDaoImp<>(Author.class);
        docnameDao = new StandardDaoImp<>(Docname.class);
        documentDao = new StandardDaoImp<>(Document.class);
        roleDao = new StandardDaoImp<>(Role.class);
    }

    public AnswerMessage handleMessage(QueryMessage queryMessage) {
        QueryMessageReader messageReader = new QueryMessageReader(queryMessage);
        try {
            switch (queryMessage.getMethod()) {
            case AUTHENTICATE -> {
                return authenticate(messageReader.getUser());
            }
            case REGISTER -> {
                return register(messageReader.getUser());
            }
            case ADD_AUTHOR -> {
                return addAuthor(messageReader.getAuthor());
            }
            case ADD_DOCNAME -> {
                return addDocname(messageReader.getDocname());
            }
            case ADD_RECEIPT -> {
                return addReceipt(messageReader.getReceipt());
            }
            case ADD_ROLE -> {
                return addRole(messageReader.getRole());
            }
            case ADD_SIMPLE_FUND -> {
                return addSimpleFund(messageReader.getSimpleFund());
            }
            case UPDATE_ROLE -> {
                return updateRole(messageReader.getRole());
            }
            case UPDATE_USER -> {
                return updateUser(messageReader.getUser());
            }
            case UPDATE_SIMPLE_FUND -> {
                return updateSimpleFund(messageReader.getSimpleFund());
            }
            case GET_ALL_ROLES -> {
                return getAllRoles();
            }
            case GET_ALL_USERS -> {
                return getAllUser();
            }
            case GET_ALL_FUNDS -> {
                return getAllFunds(messageReader.getUser());
            }
            case GET_ALL_RECEIPTS_OF_FUNDS -> {
                return getAllReceiptsOfFunds(messageReader.getFunds());
            }
            case GET_ALL_AUTHORS -> {
                return getAllAuthors();
            }
            case GET_ALL_DOCNAMES -> {
                return getAllDocnames();
            }
            case GET_ALL_SIMPLE_FUNDS -> {
                return getAllSimpleFunds(messageReader.getUser());
            }
            default -> {
                return null;
            }
        }
        } catch (QueryMessageReadException e){
            return getFailureMessage(e.getMessage());
        } catch (Exception e) {
            return getFailureMessage("Unknown Error");
        }
    }

    private AnswerMessage addReceipt(ReceiptDTO receiptDTO) {
        if(receiptDTO.getDocuments().size() == 0)
            return getFailureMessage("Can't add receipt");

        Set<Document> documents = new HashSet<>();
        for(var document : receiptDTO.getDocuments()) {
            documents.add(Document.builder().
                    name(Docname.builder().
                            id(document.getName().getId()).
                            build()).
                    build());
        }

        Receipt receipt = receiptDao.create(Receipt.builder().
                fund(Fund.builder().
                        id(receiptDTO.getFund().getId()).
                        build()).
                user(userController.getByLogin(receiptDTO.getUser().getLogin())).
                documents(documents).
                date(receiptDTO.getDate()).
                build());

        if(receipt != null) {
            Fund fund = fundDao.findById(receipt.getFund().getId());
            fund.getDocuments().addAll(documents);
            fundDao.update(fund);
        }

        return getSuccessMessage();
    }
    private AnswerMessage addAuthor(AuthorDTO authorDTO) {
        Author author = authorDao.create(Author.builder().
                name(authorDTO.getName()).
                surname(authorDTO.getSurname()).
                patronymic(authorDTO.getPatronymic()).
                build());

        AnswerMessage answer = getSuccessMessage();
        answer.setAuthors(List.of(ConverterToDTO.convertAuthor(author)));
        return answer;
    }
    private AnswerMessage addDocname(DocnameDTO docnameDTO) {
        Docname docname = docnameDao.create(Docname.builder().
                title(docnameDTO.getTitle()).
                edition(docnameDTO.getEdition()).
                isbn(docnameDTO.getIsbn()).
                releaseDate(docnameDTO.getReleaseDate()).
                author(Author.builder().
                        id(docnameDTO.getAuthor().getId()).
                        build()).
                build());

        AnswerMessage answer = getSuccessMessage();
        answer.setDocnames(List.of(ConverterToDTO.convertDocname(docname)));
        return answer;
    }
    private AnswerMessage addRole(RoleDTO roleDTO) {
        List<Fund> funds = new ArrayList<>();
        for (var fund : roleDTO.getFunds()) {
            funds.add(fundDao.findById(fund.getId()));
        }

        try {
            Role role = roleDao.create(Role.builder().
                    name(roleDTO.getName()).
                    build());

            role.setFunds(funds);
            role = roleDao.update(role);

            AnswerMessage answer = getSuccessMessage();
            answer.setRoles(ConverterToDTO.convertRoles(List.of(role)));
            return answer;
        }
        catch (PersistenceException ex) {
            return getFailureMessage("Can't add role");
        }
    }
    private AnswerMessage addSimpleFund(SimpleFundDTO fundDTO) {
        return getFailureMessage("not implemented yet");
    }
    private AnswerMessage updateUser(UserDTO userDTO) {
        User user = userController.updateUser(User.builder().
                id(userDTO.getId()).
                name(userDTO.getName()).
                role(Role.builder().
                        id(userDTO.getRole().getId()).
                        build()).
                surname(userDTO.getSurname()).
                login(userDTO.getLogin()).
                pass(userDTO.getPass()).
                build());

        AnswerMessage answer = getSuccessMessage();
        answer.setUsers(List.of(ConverterToDTO.convertUser(user)));

        return answer;
    }
    private AnswerMessage updateSimpleFund(SimpleFundDTO fundDTO) {
        Fund fund = fundDao.findById(fundDTO.getId());

        if(fund != null)
            fund.setName(fundDTO.getName());
        else
            return getFailureMessage("Can't update fund");

        var finalFund = fundDao.update(fund);

        AnswerMessage answer = getSuccessMessage();
        answer.setSimpleFunds(List.of(ConverterToDTO.convertSimpleFund(finalFund)));

        return answer;
    }
    private AnswerMessage updateRole(RoleDTO roleDTO) {
        List<Fund> funds = new ArrayList<>();
        for (var fund : roleDTO.getFunds()) {
            funds.add(fundDao.findById(fund.getId()));
        }

        Role role = roleDao.update(Role.builder().
                id(roleDTO.getId()).
                name(roleDTO.getName()).
                funds(funds).
                build());

        AnswerMessage answer = getSuccessMessage();
        answer.setRoles(ConverterToDTO.convertRoles(List.of(role)));
        return answer;
    }
    private AnswerMessage authenticate(UserDTO userDTO) {
        if (!userController.authenticate(userDTO.getLogin(), userDTO.getPass()))
            return getFailureMessage("Wrong login or password!");
        else {
            AnswerMessage answer = getSuccessMessage();
            answer.setUsers(List.of(ConverterToDTO.convertUser(userController.getByLogin(userDTO.getLogin()))));
            return answer;
        }
    }
    private AnswerMessage register(UserDTO userDTO) {
        if (!userController.register(User.builder().
                name(userDTO.getName()).
                surname(userDTO.getSurname()).
                login(userDTO.getLogin()).
                pass(userDTO.getPass()).
                build()))
            return getFailureMessage("Fail to register this user, try again");
        else
            return getSuccessMessage();
    }
    private AnswerMessage getAllFunds(UserDTO userDTO) {
        List<Fund>funds = userController.getAllFunds(userDTO.getLogin());

        AnswerMessage answer = getSuccessMessage();
        answer.setFunds(ConverterToDTO.convertFunds(funds));
        return answer;
    }
    private AnswerMessage getAllDocnames() {
        AnswerMessage answer = getSuccessMessage();
        answer.setDocnames(ConverterToDTO.convertDocnames(docnameDao.findAll()));
        return answer;
    }
    private AnswerMessage getAllAuthors() {
        AnswerMessage answer = getSuccessMessage();
        answer.setAuthors(ConverterToDTO.convertAuthors(authorDao.findAll()));
        return answer;
    }
    private AnswerMessage getAllReceiptsOfFunds(List<FundDTO> fundDTOS) {
        List<Receipt> receipts = new ArrayList<>();
        for(var fund : fundDTOS) {
            receipts.addAll(receiptDao.findByColumn("fund", fundDao.findById(fund.getId())));
        }

        AnswerMessage answer = getSuccessMessage();
        answer.setReceipts(ConverterToDTO.convertReceipts(receipts));
        return answer;
    }
    private AnswerMessage getAllRoles() {
        AnswerMessage answer = getSuccessMessage();
        answer.setRoles(ConverterToDTO.convertRoles(userController.findAllRoles()));
        return answer;
    }
    private AnswerMessage getAllUser() {
        AnswerMessage answer = getSuccessMessage();
        answer.setUsers(ConverterToDTO.convertUsers(userController.findAllUsers()));
        return answer;
    }
    private AnswerMessage getAllSimpleFunds(UserDTO userDTO) {
        List<Fund>funds = userController.getAllFunds(userDTO.getLogin());

        AnswerMessage answer = getSuccessMessage();
        answer.setSimpleFunds(ConverterToDTO.convertSimpleFunds(funds));
        return answer;
    }

    private AnswerMessage getFailureMessage(String message) {
        return AnswerMessage.builder().
                result(MessageResult.FAILURE).
                message(message).
                build();
    }

    private AnswerMessage getSuccessMessage() {
        return AnswerMessage.builder().
                result(MessageResult.SUCCESS).
                message("").
                build();
    }
}
