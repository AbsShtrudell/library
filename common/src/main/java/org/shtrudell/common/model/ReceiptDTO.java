package org.shtrudell.common.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link org.shtrudell.server.model.Receipt} entity
 */
@Data
public class ReceiptDTO implements Serializable {
    private final Integer id;
    private final LocalDate date;
    private final UserDTO user;
    private final FundDTO fund;
}