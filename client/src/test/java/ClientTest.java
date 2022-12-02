import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.shtrudell.client.net.Client;
import org.shtrudell.common.model.RoleDTO;
import org.shtrudell.common.model.UserDTO;
import org.shtrudell.common.net.MessageMethod;
import org.shtrudell.common.net.MessageResult;
import org.shtrudell.common.net.QueryMessage;
import org.shtrudell.common.util.HashPassword;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class ClientTest {
    public static void main(String[] args) {
        Client client;
        client = new Client();
        try {
            client.connect("127.0.0.1", 8080);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            client.sendMsg(QueryMessage.builder().
                    method(MessageMethod.AUTHENTICATE).
                    user(UserDTO.builder().
                            login("Admin").
                            pass(HashPassword.getHash("Admin")).
                            id(1).
                            name("lox").
                            surname("loxovich").
                            role(RoleDTO.builder().
                                    id(1).
                                    build()).
                            build()).
                    build());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        if(client.receiveMsg().getResult().equals(MessageResult.SUCCESS))
            System.out.println("Authenticated");
    }
}
