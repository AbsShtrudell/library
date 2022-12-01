package org.shtrudell.common.net;

import lombok.Getter;
import org.shtrudell.common.model.*;

import java.io.Serializable;
import java.util.List;

@Getter
public class Message implements Serializable {
    List<AuthorDTO> authors;
    List<DocumentDTO> documents;
    List<DocnameDTO> docnames;
    List<FundDTO> funds;
    List<ReceiptDTO> receipts;
    List<RoleDTO> roles;
    List<UserDTO> users;

    MessageType type;

    public Message() {

    }
}
