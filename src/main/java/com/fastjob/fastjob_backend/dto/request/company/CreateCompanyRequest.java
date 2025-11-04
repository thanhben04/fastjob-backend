package com.fastjob.fastjob_backend.dto.request.company;

import com.fastjob.fastjob_backend.constant.CompanySizeEnum;

public record CreateCompanyRequest(
        String companyName,
        String address,
        String provinceCode,
        String avatarUrl,
        String website,
        String phone,
        String email,
        String description,
        String industry,
        CompanySizeEnum companySizeEnum
) {}

