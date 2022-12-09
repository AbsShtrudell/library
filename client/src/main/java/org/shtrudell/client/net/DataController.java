package org.shtrudell.client.net;

import org.shtrudell.common.model.FundDTO;
import org.shtrudell.common.model.ReceiptDTO;
import org.shtrudell.common.model.UserDTO;

import java.util.List;

public interface DataController {
    boolean authenticate(String login, byte[] password);

    void register(UserDTO user);

    List<FundDTO> getAllFunds();

    List<ReceiptDTO> getAllReceiptsOfFunds(List<FundDTO> funds);
}
