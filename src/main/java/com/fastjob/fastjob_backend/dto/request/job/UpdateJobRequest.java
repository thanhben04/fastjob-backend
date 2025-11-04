package com.fastjob.fastjob_backend.dto.request.job;

import com.fastjob.fastjob_backend.constant.EducationLevelEnum;
import com.fastjob.fastjob_backend.constant.JobLevelEnum;
import com.fastjob.fastjob_backend.constant.WorkTypeEnum;

import java.time.LocalDate;

public record UpdateJobRequest(
        String title,
        String requirement,
        String description,
        String benefit,
        Double minSalary,
        Double maxSalary,
        String address,
        String provinceCode,
        Integer experience,
        EducationLevelEnum educationLevelEnum,
        JobLevelEnum jobLevel,
        Integer quantity,
        WorkTypeEnum workType,
        LocalDate expirationDate,
        Long companyId
) {}

