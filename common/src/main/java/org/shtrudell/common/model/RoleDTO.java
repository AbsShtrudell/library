package org.shtrudell.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link org.shtrudell.server.model.Role} entity
 */
@Data
public class RoleDTO implements Serializable {
    private final Integer id;
    private final String name;
}