package org.shtrudell.common.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * A DTO for the {@link org.shtrudell.server.model.Receipt} entity
 */
@Data
@Builder
@ToString
public class ReceiptDTO implements Serializable {
    private final Integer id;
    private final LocalDate date;
    private final UserDTO user;
    private final FundDTO fund;
    private final List<DocumentDTO> documents;
}