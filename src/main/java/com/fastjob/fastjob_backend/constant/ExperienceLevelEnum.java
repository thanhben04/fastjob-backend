package com.fastjob.fastjob_backend.constant;

import lombok.Getter;

@Getter
public enum ExperienceLevelEnum {
    NO_EXPERIENCE("Chưa có kinh nghiệm"),
    LESS_THAN_1_YEAR("Dưới 1 năm"),
    ONE_YEAR("1 năm"),
    TWO_YEARS("2 năm"),
    THREE_YEARS("3 năm"),
    FOUR_YEARS("4 năm"),
    FIVE_YEARS("5 năm"),
    MORE_THAN_5_YEARS("Hơn 5 năm");

    public final String label;

    ExperienceLevelEnum(String label) {
        this.label = label;
    }
}
