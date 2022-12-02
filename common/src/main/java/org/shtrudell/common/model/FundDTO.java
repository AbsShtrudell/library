package org.shtrudell.common.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link org.shtrudell.server.model.Fund} entity
 */
@Data
@Builder
@ToString
public class FundDTO implements Serializable {
    private final Integer id;
    private final String name;
    private final List<DocumentDTO> documents;
}