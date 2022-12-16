package org.shtrudell.common.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class SimpleFundDTO implements Serializable {
    private final Integer id;
    private String name;

    public SimpleFundDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public SimpleFundDTO(FundDTO fund) {
        this.id = fund.getId();
        this.name = fund.getName();
    }
}
