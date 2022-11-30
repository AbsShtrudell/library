package org.shtrudell.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link org.shtrudell.server.model.User} entity
 */
@Data
public class UserDTO implements Serializable {
    private final Integer id;
    private final String name;
    private final String surname;
    private final byte[] pass;
    private final String login;
    private final RoleDTO role;
}