package org.shtrudell.server.net;

import org.shtrudell.common.net.AnswerMessage;
import org.shtrudell.common.net.MessageResult;
import org.shtrudell.common.net.QueryMessage;
import org.shtrudell.server.controller.UserController;
import org.shtrudell.server.model.Role;
import org.shtrudell.server.model.User;

public class MessageHandler {
    UserController userController;

    public MessageHandler() {
        userController = new UserController();
    }

    public AnswerMessage handleMessage(QueryMessage queryMessage) {
        switch (queryMessage.getMethod()) {
            case AUTHENTICATE -> {
                if (queryMessage.getUsers().size() == 0)
                    return getFailureMessage("No user to authenticate!");
                var user = queryMessage.getUsers().get(0);
                if (!userController.authenticate(user.getName(), user.getPass()))
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
                        role(Role.builder().
                                id(user.getRole().getId()).
                                build()).
                        build()))
                    return getFailureMessage("Fail to register this user, try again");
                else
                    return getSuccessMessage();
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
                result(MessageResult.FAILURE).
                message("").
                build();
    }
}
