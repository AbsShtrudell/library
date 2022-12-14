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
public class DocumentDTO implements Serializable {
    private final Integer id;
    private final DocnameDTO name;

    @Override
    public String toString() {
        return String.format("%d: \"%s\" %s %s %s %d ed., isbn: %d", id, name.getTitle(), name.getAuthor().getSurname(),
                name.getAuthor().getName(), name.getAuthor().getPatronymic(), name.getEdition(), name.getIsbn());
    }
}