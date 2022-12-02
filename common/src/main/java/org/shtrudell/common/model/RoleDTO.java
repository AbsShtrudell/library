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
    private final String name;
    @Singular
    private final List<Fund> funds = new ArrayList<>();

    @Data
    @Builder
    public static class Fund {
        private final Integer id;
        private final String name;
    }
}