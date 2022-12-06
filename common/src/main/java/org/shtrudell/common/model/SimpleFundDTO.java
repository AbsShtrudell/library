package org.shtrudell.common.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimpleFundDTO {
    private final Integer id;
    private final String name;

    public SimpleFundDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public SimpleFundDTO(FundDTO fund) {
        this.id = fund.getId();
        this.name = fund.getName();
    }
}
