package org.shtrudell.client.net;

import org.shtrudell.common.model.*;

import java.util.List;

public interface DataController {
    UserDTO getUser();
    boolean authenticate(String login, byte[] password);
    void register(UserDTO user);
    List<FundDTO> getAllFunds();
    List<ReceiptDTO> getAllReceiptsOfFunds(List<FundDTO> funds);
    List<DocnameDTO> getAllDocnames();
    List<AuthorDTO> getAllAuthors();
    List<RoleDTO> getAllRoles();
    List<UserDTO> getAllUsers();
    RoleDTO addRole(RoleDTO role);
    FundDTO addFund(FundDTO fund);
    DocnameDTO addDocname(DocnameDTO docname);
    AuthorDTO addAuthor(AuthorDTO author);
    void addReceipt(ReceiptDTO receipt);
    UserDTO updateUser(UserDTO user);
    FundDTO updateFund(FundDTO fund);
    RoleDTO updateRole(RoleDTO role);
}
