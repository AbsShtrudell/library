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
        switch (queryMessage.getMethod()) {
            case AUTHENTICATE -> {
                if (queryMessage.getUsers().size() == 0)
                    return getFailureMessage("No user to authenticate!");
                var user = queryMessage.getUsers().get(0);
                if (!userController.authenticate(user.getLogin(), user.getPass()))
                    return getFailureMessage("Wrong login or password!");
                else {
                    AnswerMessage answer = getSuccessMessage();
                    answer.setUsers(List.of(ConverterToDTO.convertUser(userController.getByLogin(user.getLogin()))));
                    return answer;
                }
            }
            case REGISTER -> {
                if (queryMessage.getUsers().size() == 0)
                    return getFailureMessage("No user to Register!");
                var user = queryMessage.getUsers().get(0);
                if (!userController.register(User.builder().
                        name(user.getName()).
                        surname(user.getSurname()).
                        login(user.getLogin()).
                        pass(user.getPass()).
                        build()))
                    return getFailureMessage("Fail to register this user, try again");
                else
                    return getSuccessMessage();
            }
            case GET_ALL_FUNDS -> {
                if (queryMessage.getUsers().size() == 0)
                    return getFailureMessage("Can't get funds");
                var user = queryMessage.getUsers().get(0);
                List<Fund>funds = userController.getAllFunds(queryMessage.getUsers().get(0).getLogin());
                AnswerMessage answer = getSuccessMessage();
                answer.setFunds(ConverterToDTO.convertFunds(funds));
                return answer;
            }
            case GET_ALL_RECEIPTS_OF_FUNDS -> {
                if (queryMessage.getFunds().size() == 0)
                    return getFailureMessage("Can't get receipts");

                List<FundDTO>funds = queryMessage.getFunds();
                List<Receipt> receipts = new ArrayList<>();
                for(var fund : funds) {
                    receipts.addAll(receiptDao.findByColumn("fund", fundDao.findById(fund.getId())));
                }

                AnswerMessage answer = getSuccessMessage();
                answer.setReceipts(ConverterToDTO.convertReceipts(receipts));
                return answer;
            }
            case GET_ALL_AUTHORS -> {
                AnswerMessage answer = getSuccessMessage();
                answer.setAuthors(ConverterToDTO.convertAuthors(authorDao.findAll()));
                return answer;
            }
            case GET_ALL_DOCNAMES -> {
                AnswerMessage answer = getSuccessMessage();
                answer.setDocnames(ConverterToDTO.convertDocnames(docnameDao.findAll()));
                return answer;
            }
            case ADD_AUTHOR -> {
                if(queryMessage.getAuthors().size() == 0 || queryMessage.getAuthors().get(0) == null)
                    return getFailureMessage("Can't add author");

                AuthorDTO authorDTO = queryMessage.getAuthors().get(0);

                Author author = authorDao.create(Author.builder().
                        name(authorDTO.getName()).
                        surname(authorDTO.getSurname()).
                        patronymic(authorDTO.getPatronymic()).
                        build());
                AnswerMessage answer = getSuccessMessage();
                answer.setAuthors(List.of(ConverterToDTO.convertAuthor(author)));
                return answer;
            }
            case ADD_DOCNAME -> {
                if(queryMessage.getDocnames().size() == 0 || queryMessage.getDocnames().get(0) == null)
                    return getFailureMessage("Can't add docname");

                DocnameDTO docnameDTO = queryMessage.getDocnames().get(0);

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
            case NEW_RECEIPT -> {
                if(queryMessage.getReceipts().size() == 0 || queryMessage.getReceipts().get(0).getDocuments().size() == 0
                        || queryMessage.getReceipts().get(0) == null)
                    return getFailureMessage("Can't add receipt");
                ReceiptDTO receiptDTO = queryMessage.getReceipts().get(0);
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

                return null;
            }
            case UPDATE_USER -> {
                if(queryMessage.getUsers().size() == 0 || queryMessage.getUsers().get(0) == null)
                    return getFailureMessage("Can't update user");

                UserDTO userDTO = queryMessage.getUsers().get(0);

                User user = userController.update(User.builder().
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
            case ADD_ROLE -> {
                if(queryMessage.getRoles().size() == 0 || queryMessage.getRoles().get(0) == null)
                    return getFailureMessage("Can't add role");

                RoleDTO roleDTO = queryMessage.getRoles().get(0);

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
            case UPDATE_ROLE -> {
                if(queryMessage.getRoles().size() == 0 || queryMessage.getRoles().get(0) == null)
                    return getFailureMessage("Can't update role");

                RoleDTO roleDTO = queryMessage.getRoles().get(0);

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
            case GET_ALL_ROLES -> {
                AnswerMessage answer = getSuccessMessage();
                answer.setRoles(ConverterToDTO.convertRoles(userController.findAllRoles()));
                return answer;
            }
            case GET_ALL_USERS -> {
                AnswerMessage answer = getSuccessMessage();
                answer.setUsers(ConverterToDTO.convertUsers(userController.findAllUsers()));
                return answer;
            }
            default -> {
                return null;
            }
        }
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
