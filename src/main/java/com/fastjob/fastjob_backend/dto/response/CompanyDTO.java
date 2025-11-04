package com.fastjob.fastjob_backend.dto.response;

import com.fastjob.fastjob_backend.constant.CompanySizeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private Long id;
    private String companyName;
    private String address;
    private String provinceCode;
    private String avatarUrl;
    private String website;
    private String phone;
    private String email;
    private String description;
    private String industry;
    private CompanySizeEnum companySizeEnum;
}

