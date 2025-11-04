package com.fastjob.fastjob_backend.dto.response.job;

import com.fastjob.fastjob_backend.entity.Job;

public record JobResponse(
        Long id,
        String title,
        String requirement,
        String description,
        String benefit,
        Double minSalary,
        Double maxSalary,
        String address,
        String provinceCode,
        Integer experience,
        String educationLevel,   // label tiếng Việt
        String jobLevel,         // label tiếng Việt
        Integer quantity,
        String workType,         // label tiếng Việt
        String companyName,
        String companyLogo,
        String industry,
        String companySize,
        String expirationDate
) {

    public static JobResponse fromEntity(Job job) {
        return new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getRequirement(),
                job.getDescription(),
                job.getBenefit(),
                job.getMinSalary(),
                job.getMaxSalary(),
                job.getAddress(),
                job.getProvinceCode(),
                job.getExperience(),
                job.getEducationLevelEnum() != null ? job.getEducationLevelEnum().getLabel() : null,
                job.getJobLevel() != null ? job.getJobLevel().getLabel() : null,
                job.getQuantity(),
                job.getWorkType() != null ? job.getWorkType().getLabel() : null,
                job.getCompany().getCompanyName(),
                job.getCompany().getAvatarUrl(),
                job.getCompany().getIndustry(),
                job.getCompany().getCompanySizeEnum() != null ? job.getCompany().getCompanySizeEnum().getLabel() : null,
                job.getExpirationDate() != null ? job.getExpirationDate().toString() : null
        );
    }
}