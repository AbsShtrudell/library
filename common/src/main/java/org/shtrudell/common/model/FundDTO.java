package org.shtrudell.common.model;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link org.shtrudell.server.model.Fund} entity
 */
@Data
@Builder
public class FundDTO implements Serializable {
    private final Integer id;
    private final String name;
    @Singular
    private final List<DocumentDTO> documents;
}