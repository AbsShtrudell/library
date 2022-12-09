package org.shtrudell.server.controller;

import jakarta.persistence.PersistenceException;
import org.shtrudell.server.integration.StandardDao;
import org.shtrudell.server.integration.StandardDaoImp;
import org.shtrudell.server.model.Fund;
import org.shtrudell.server.model.Role;
import org.shtrudell.server.model.User;

import java.util.Arrays;
import java.util.List;

public class UserController {
    StandardDao<User> userDao;
    StandardDao<Role> roleDao;

    public UserController() {
        this.userDao = new StandardDaoImp<>(User.class);
        this.roleDao = new StandardDaoImp<>(Role.class);
    }

    public boolean authenticate(String login, byte[] password) {
        if(login == null || password == null) return false;

        var user = userDao.findByUniqueColumn("login", login);

        if(user == null) return false;
        return Arrays.equals(user.getPass(),password);
    }

    public boolean register(User user) {
        if(user == null) return false;
        user.setRole(roleDao.findByUniqueColumn("name", "User"));
        try {
            userDao.create(user);
            return true;
        } catch (PersistenceException e) {
            return false;
        }
    }

    public List<Fund> getAllFunds(String login) {
        User user = userDao.findByUniqueColumn("login", login);
        if(user == null) return null;
        else return user.getRole().getFunds();
    }
}
