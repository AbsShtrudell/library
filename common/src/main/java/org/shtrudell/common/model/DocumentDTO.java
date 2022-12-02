package org.shtrudell.common.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * A DTO for the {@link org.shtrudell.server.model.Document} entity
 */
@Data
@Builder
@ToString
public class DocumentDTO implements Serializable {
    private final Integer id;
    private final DocnameDTO name;
}