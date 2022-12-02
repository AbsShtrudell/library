package org.shtrudell.common.net;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.shtrudell.common.model.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class QueryMessage implements Serializable {
    List<AuthorDTO> authors;
    List<DocumentDTO> documents;
    List<DocnameDTO> docnames;
    List<FundDTO> funds;
    List<ReceiptDTO> receipts;
    List<RoleDTO> roles;
    List<UserDTO> users;

    private MessageMethod method;
}
