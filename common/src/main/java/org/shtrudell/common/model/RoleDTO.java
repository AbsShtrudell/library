package org.shtrudell.common.model;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the {@link org.shtrudell.server.model.Role} entity
 */
@Data
@Builder
public class RoleDTO implements Serializable {
    private final Integer id;
    private String name;
    @Singular
    private List<SimpleFundDTO> funds;

    @Override
    public String toString() {
        return name;
    }
}