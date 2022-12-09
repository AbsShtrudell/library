package org.shtrudell.server.net;

import org.shtrudell.common.model.AuthorDTO;
import org.shtrudell.common.model.DocnameDTO;
import org.shtrudell.common.model.DocumentDTO;
import org.shtrudell.common.model.FundDTO;
import org.shtrudell.common.net.AnswerMessage;
import org.shtrudell.common.net.MessageResult;
import org.shtrudell.common.net.QueryMessage;
import org.shtrudell.server.controller.UserController;
import org.shtrudell.server.integration.StandardDao;
import org.shtrudell.server.integration.StandardDaoImp;
import org.shtrudell.server.model.*;

import java.util.ArrayList;
import java.util.List;

public class MessageHandler {
    UserController userController;
    StandardDao<Receipt> receiptDao;
    StandardDao<Fund> fundDao;

    public MessageHandler() {
        userController = new UserController();
        receiptDao = new StandardDaoImp<>(Receipt.class);
        fundDao = new StandardDaoImp<>(Fund.class);
    }

    public AnswerMessage handleMessage(QueryMessage queryMessage) {
        switch (queryMessage.getMethod()) {
            case AUTHENTICATE -> {
                if (queryMessage.getUsers().size() == 0)
                    return getFailureMessage("No user to authenticate!");
                var user = queryMessage.getUsers().get(0);
                if (!userController.authenticate(user.getLogin(), user.getPass()))
                    return getFailureMessage("Wrong login or password!");
                else
                    return getSuccessMessage();
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
