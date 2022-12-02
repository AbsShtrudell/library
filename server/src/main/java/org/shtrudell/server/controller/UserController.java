package org.shtrudell.server.controller;

import jakarta.persistence.PersistenceException;
import org.shtrudell.server.integration.StandardDao;
import org.shtrudell.server.integration.StandardDaoImp;
import org.shtrudell.server.model.User;

import java.util.Arrays;

public class UserController {
    StandardDao<User> userDao;

    public UserController() {
        this.userDao = new StandardDaoImp<>(User.class);
    }

    public boolean authenticate(String login, byte[] password) {
        if(login == null || password == null) return false;

        var user = userDao.findByUniqueColumn("login", login);

        if(user == null) return false;
        return Arrays.equals(user.getPass(),password);
    }

    public boolean register(User user) {
        if(user == null) return false;
        try {
            userDao.create(user);
            return true;
        } catch (PersistenceException e) {
            return false;
        }
    }
}
