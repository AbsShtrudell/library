package org.shtrudell.common.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link org.shtrudell.server.model.Docname} entity
 */
@Data
@Builder
public class DocnameDTO implements Serializable {
    private final Integer id;
    private final String title;
    private final Integer edition;
    private final LocalDate releaseDate;
    private final AuthorDTO author;
    private final Integer isbn;
}