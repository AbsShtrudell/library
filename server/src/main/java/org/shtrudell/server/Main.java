package org.shtrudell.server;


import org.shtrudell.server.integration.StandardDaoImp;
import org.shtrudell.server.model.Role;

public class Main {
    public static void main(String[] args) {
        Role role = Role.builder().name("Admin").build();

        StandardDaoImp<Role> dao = new StandardDaoImp<>(Role.class);
        dao.create(role);
    }
}