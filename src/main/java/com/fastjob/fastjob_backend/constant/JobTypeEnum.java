package com.fastjob.fastjob_backend.constant;

import lombok.Getter;

@Getter
public enum JobTypeEnum {

    VIEC_LAM_TUYEN_GAP("Việc làm tuyển gấp"),
    VIEC_DI_LAM_NGAY("Việc đi làm ngay");

    public final String label;

    JobTypeEnum(String label) {
        this.label = label;
    }
}