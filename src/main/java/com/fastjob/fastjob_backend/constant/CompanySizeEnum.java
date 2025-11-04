package com.fastjob.fastjob_backend.constant;

import lombok.Getter;

@Getter
public enum CompanySizeEnum {

    UNDER_10("< 10"),
    FROM_10_TO_50("10 - 50"),
    FROM_50_TO_100("50 - 100"),
    FROM_100_TO_200("100 - 200"),
    OVER_200("> 200");

    public final String label;

    CompanySizeEnum(String label) {
        this.label = label;
    }
}
