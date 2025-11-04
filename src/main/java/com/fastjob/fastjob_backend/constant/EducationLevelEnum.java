package com.fastjob.fastjob_backend.constant;

import lombok.Getter;

@Getter
public enum EducationLevelEnum {
  HIGH_SCHOOL("THPT"),
  INTERMEDIATE("Trung cấp"),
  COLLEGE("Cao đẳng"),
  BACHELOR("Đại học"),
  MASTER("Thạc sĩ"),
  DOCTOR("Tiến sĩ");

  public final String label;

  EducationLevelEnum(String label) {
    this.label = label;
  }
}