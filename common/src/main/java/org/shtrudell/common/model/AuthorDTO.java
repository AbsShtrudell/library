package org.shtrudell.common.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * A DTO for the {@link org.shtrudell.server.model.Author} entity
 */
@Data
@Builder
@ToString
public class AuthorDTO implements Serializable {
    private final Integer id;
    private final String name;
    private final String surname;
    private final String patronymic;
}