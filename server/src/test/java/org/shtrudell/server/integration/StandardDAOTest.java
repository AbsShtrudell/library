package org.shtrudell.server.integration;

import jakarta.persistence.PersistenceException;
import org.junit.*;
import org.shtrudell.common.util.HashPassword;
import org.shtrudell.server.model.Fund;
import org.shtrudell.server.model.Role;
import org.shtrudell.server.model.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class StandardDAOTest {

    static StandardDaoImp<Role> roleDao;
    static StandardDaoImp<User> userDao;

    @BeforeClass
    public static void setup() {
        roleDao = new StandardDaoImp<>(Role.class);
        userDao = new StandardDaoImp<>(User.class);
    }

    @Test
    public void create() {
        try {
            Role role = Role.builder().name("Admin").build();
            roleDao.create(role);
            roleDao.create(role);
            Role role2 = Role.builder().name("User").build();
            roleDao.create(role2);

            User user = User.builder().login("gg").pass(HashPassword.getHash("1234")).role(role).build();
            userDao.create(user);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | PersistenceException ignore) {
        }
    }

    @Test
    public void findAll() {
        System.out.println(roleDao.findAll());
    }

    @Test
    public void findByColumn() {
        System.out.println(roleDao.findByColumn("name", "Admin"));
    }

    @Test
    public void findById() {
        System.out.println(roleDao.findById(5));
    }

    @Test
    public void update() {
        Role role = roleDao.findByUniqueColumn("name", "Admin");
        role.getFunds().add(Fund.builder().name("TestFund8").build());
        //role.getFunds().add(Fund.builder().name("TestFund").id(2).build());
        //role.getFunds().add(Fund.builder().name("TestFund2").id(6).build());
        System.out.println(roleDao.update(role));
    }

    @Test
    public void delete() {
        var list = roleDao.findByColumn("name", "User");
        list.add(null);
        roleDao.delete(list);
    }

    @Test
    public void deleteById() {
        roleDao.delete(6);
    }

    //@AfterClass
    public static void testDelete() {
        StandardDaoImp<Role> dao = new StandardDaoImp<>(Role.class);
        dao.delete(dao.findByColumn("name", "Admin"));
    }
}