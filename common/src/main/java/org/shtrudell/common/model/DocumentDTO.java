package org.shtrudell.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link org.shtrudell.server.model.Document} entity
 */
@Data
public class DocumentDTO implements Serializable {
    private final Integer id;
    private final DocnameDTO name;
}