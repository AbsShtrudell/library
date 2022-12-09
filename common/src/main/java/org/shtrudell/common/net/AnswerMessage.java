package org.shtrudell.common.net;

import lombok.Builder;
import lombok.Getter;

import lombok.Setter;
import lombok.Singular;
import org.shtrudell.common.model.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class AnswerMessage implements Serializable {
    @Singular
    List<AuthorDTO> authors;
    @Singular
    List<DocumentDTO> documents;
    @Singular
    List<DocnameDTO> docnames;
    @Singular
    List<FundDTO> funds;
    @Singular
    List<ReceiptDTO> receipts;
    @Singular
    List<RoleDTO> roles;
    @Singular
    List<UserDTO> users;

    private MessageResult result;
    private String message;
}
