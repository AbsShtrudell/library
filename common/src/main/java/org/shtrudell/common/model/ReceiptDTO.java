package org.shtrudell.common.model;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * A DTO for the {@link org.shtrudell.server.model.Receipt} entity
 */
@Data
@Builder
public class ReceiptDTO implements Serializable {
    private Integer id;
    private LocalDate date;
    private UserDTO user;
    private FundDTO fund;
    @Singular
    private List<DocumentDTO> documents;
}