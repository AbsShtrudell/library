package org.shtrudell.client.net;

import org.shtrudell.common.model.*;

import java.util.List;

public interface DataController {
    boolean authenticate(String login, byte[] password) throws DataOperationException;
    void register(UserDTO user) throws DataOperationException;
    RoleDTO addRole(RoleDTO role) throws DataOperationException;
    DocnameDTO addDocname(DocnameDTO docname) throws DataOperationException;
    AuthorDTO addAuthor(AuthorDTO author) throws DataOperationException;
    void addReceipt(ReceiptDTO receipt) throws DataOperationException;
    UserDTO updateUser(UserDTO user) throws DataOperationException;
    RoleDTO updateRole(RoleDTO role) throws DataOperationException;
    SimpleFundDTO updateSimpleFund(SimpleFundDTO fund) throws DataOperationException;
    UserDTO getUser() throws DataOperationException;
    List<FundDTO> getAllFunds() throws DataOperationException;
    List<SimpleFundDTO> getAllSimpleFunds() throws DataOperationException;
    List<ReceiptDTO> getAllReceiptsOfFunds(List<FundDTO> funds) throws DataOperationException;
    List<DocnameDTO> getAllDocnames() throws DataOperationException;
    List<AuthorDTO> getAllAuthors() throws DataOperationException;
    List<RoleDTO> getAllRoles() throws DataOperationException;
    List<UserDTO> getAllUsers() throws DataOperationException;
}
