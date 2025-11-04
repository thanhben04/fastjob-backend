package com.fastjob.fastjob_backend.constant;

import lombok.Getter;

@Getter
public enum JobLevelEnum {

    DIRECTOR("Giám đốc"),
    MANAGER("Trưởng phòng"),
    TEAM_LEAD("Trưởng nhóm"),
    EXPERT("Chuyên gia"),
    STAFF("Nhân viên"),
    COLLABORATOR("Cộng tác viên");

    public final String label;

    JobLevelEnum(String label) {
        this.label = label;
    }
}