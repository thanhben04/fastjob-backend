package com.fastjob.fastjob_backend.mapper;

import com.fastjob.fastjob_backend.dto.response.CompanyDTO;
import com.fastjob.fastjob_backend.entity.Company;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyMapper {

    public CompanyDTO toDTO(Company company) {
        if (company == null) {
            return null;
        }

        return CompanyDTO.builder()
                .id(company.getId())
                .companyName(company.getCompanyName())
                .address(company.getAddress())
                .provinceCode(company.getProvinceCode())
                .avatarUrl(company.getAvatarUrl())
                .website(company.getWebsite())
                .phone(company.getPhone())
                .email(company.getEmail())
                .description(company.getDescription())
                .industry(company.getIndustry())
                .companySizeEnum(company.getCompanySizeEnum())
                .build();
    }

    public List<CompanyDTO> toDTOs(List<Company> companies) {
        if (companies == null) {
            return null;
        }

        List<CompanyDTO> list = new ArrayList<>(companies.size());
        for (Company company : companies) {
            list.add(toDTO(company));
        }

        return list;
    }
}

