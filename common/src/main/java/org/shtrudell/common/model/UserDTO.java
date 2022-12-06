package org.shtrudell.common.model;

import lombok.*;

import java.io.Serializable;

/**
 * A DTO for the {@link org.shtrudell.server.model.User} entity
 */
@Data
@Builder
public class UserDTO implements Serializable {
    private final Integer id;
    private String name;
    private String surname;
    private byte[] pass;
    private String login;
    private RoleDTO role;
}