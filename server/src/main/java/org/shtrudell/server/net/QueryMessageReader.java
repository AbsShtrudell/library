package org.shtrudell.server.net;

import org.shtrudell.common.model.*;
import org.shtrudell.common.net.QueryMessage;

import java.util.List;

public class QueryMessageReader {
    private final QueryMessage message;

    public QueryMessageReader(QueryMessage message){
        this.message = message;
    }

    public UserDTO getUser() throws QueryMessageReadException{
        if(message == null || message.getUsers() == null || message.getUsers().get(0) == null)
            throw new QueryMessageReadException("Can't read user");

        return message.getUsers().get(0);
    }

    public FundDTO getFund() throws QueryMessageReadException{
        if(message == null || message.getFunds() == null || message.getFunds().get(0) == null)
            throw new QueryMessageReadException("Can't read fund");

        return message.getFunds().get(0);
    }

    public List<FundDTO> getFunds() throws QueryMessageReadException{
        if(message == null || message.getFunds() == null || message.getFunds().size() == 0)
            throw new QueryMessageReadException("Can't read funds");

        return message.getFunds();
    }

    public DocnameDTO getDocname() throws QueryMessageReadException{
        if(message == null || message.getDocnames() == null || message.getDocnames().get(0) == null)
            throw new QueryMessageReadException("Can't read docname");

        return message.getDocnames().get(0);
    }

    public DocumentDTO getDocument() throws QueryMessageReadException{
        if(message == null || message.getDocuments() == null || message.getDocuments().get(0) == null)
            throw new QueryMessageReadException("Can't read document");

        return message.getDocuments().get(0);
    }

    public AuthorDTO getAuthor() throws QueryMessageReadException{
        if(message == null || message.getAuthors() == null || message.getAuthors().get(0) == null)
            throw new QueryMessageReadException("Can't read author");

        return message.getAuthors().get(0);
    }

    public RoleDTO getRole() throws QueryMessageReadException{
        if(message == null || message.getRoles() == null || message.getRoles().get(0) == null)
            throw new QueryMessageReadException("Can't read role");

        return message.getRoles().get(0);
    }

    public ReceiptDTO getReceipt() throws QueryMessageReadException{
        if(message == null || message.getReceipts() == null || message.getReceipts().get(0) == null)
            throw new QueryMessageReadException("Can't read receipt");

        return message.getReceipts().get(0);
    }

    public SimpleFundDTO getSimpleFund() throws QueryMessageReadException{
        if(message == null || message.getSimpleFunds() == null || message.getSimpleFunds().get(0) == null)
            throw new QueryMessageReadException("Can't read fund");

        return message.getSimpleFunds().get(0);
    }
}
