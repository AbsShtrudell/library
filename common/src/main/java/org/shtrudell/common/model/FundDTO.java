package org.shtrudell.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link org.shtrudell.server.model.Fund} entity
 */
@Data
public class FundDTO implements Serializable {
    private final Integer id;
    private final String name;
}