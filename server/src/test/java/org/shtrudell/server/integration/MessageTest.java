package org.shtrudell.server.integration;

import org.junit.Test;
import org.shtrudell.common.model.AuthorDTO;
import org.shtrudell.common.net.AnswerMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MessageTest {

    @Test
    public void create() {

        List<AuthorDTO> list = new ArrayList<>();
        list.add(AuthorDTO.builder().name("sfg").surname("dasf").build());

        try {
            ObjectOutputStream out = new ObjectOutputStream(System.out);

            out.writeObject(AnswerMessage.builder().authors(list).build());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
