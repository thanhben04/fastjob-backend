package com.fastjob.fastjob_backend.constant;

import lombok.Getter;

@Getter
public enum WorkTypeEnum {

    FULL_TIME_PERMANENT("Toàn thời gian cố định"),
    FULL_TIME_TEMPORARY("Toàn thời gian tạm thời"),
    PART_TIME_PERMANENT("Bán thời gian cố định"),
    PART_TIME_TEMPORARY("Bán thời gian tạm thời"),
    CONTRACT("Theo hợp đồng tư vấn"),
    INTERN("Thực tập"),
    OTHER("Khác");

    public final String label;

    WorkTypeEnum(String label) {
        this.label = label;
    }
}