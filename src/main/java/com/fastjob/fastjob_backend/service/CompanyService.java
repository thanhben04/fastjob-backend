package com.fastjob.fastjob_backend.service;

import com.fastjob.fastjob_backend.Util.SpecificationUtil;
import com.fastjob.fastjob_backend.dto.request.company.CreateCompanyRequest;
import com.fastjob.fastjob_backend.dto.request.company.UpdateCompanyRequest;
import com.fastjob.fastjob_backend.dto.response.CompanyDTO;
import com.fastjob.fastjob_backend.dto.response.PageResponse;
import com.fastjob.fastjob_backend.entity.Company;
import com.fastjob.fastjob_backend.exception.CompanyNotFoundException;
import com.fastjob.fastjob_backend.mapper.CompanyMapper;
import com.fastjob.fastjob_backend.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyMapper companyMapper;

    // Create company
    public CompanyDTO createCompany(CreateCompanyRequest request) {
        Company company = Company.builder()
                .companyName(request.companyName())
                .address(request.address())
                .provinceCode(request.provinceCode())
                .avatarUrl(request.avatarUrl())
                .website(request.website())
                .phone(request.phone())
                .email(request.email())
                .description(request.description())
                .industry(request.industry())
                .companySizeEnum(request.companySizeEnum())
                .build();

        Company savedCompany = companyRepository.save(company);
        return companyMapper.toDTO(savedCompany);
    }

    // Get company by id
    public CompanyDTO getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with id: " + id));
        return companyMapper.toDTO(company);
    }

    // Get all companies
    public List<CompanyDTO> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companyMapper.toDTOs(companies);
    }

    // Get companies with pagination and search
    public PageResponse<CompanyDTO> getCompaniesWithPagination(
            String search,
            String provinceCode,
            String industry,
            String sortBy,
            String sortDir,
            int page,
            int size) {
        
        // Tạo Specification từ các tham số tìm kiếm và lọc
        Specification<Company> spec = SpecificationUtil.buildCompanySpecification(
                search, provinceCode, industry, sortBy, sortDir);

        // Tạo Pageable với phân trang và sắp xếp
        Pageable pageable = PageRequest.of(page, size);

        // Thực hiện truy vấn với Specification và Pageable
        Page<Company> companyPage = companyRepository.findAll(spec, pageable);

        // Chuyển đổi Page<Company> sang PageResponse<CompanyDTO>
        List<CompanyDTO> companyDTOs = companyMapper.toDTOs(companyPage.getContent());

        return PageResponse.<CompanyDTO>builder()
                .content(companyDTOs)
                .page(companyPage.getNumber())
                .size(companyPage.getSize())
                .totalElements(companyPage.getTotalElements())
                .totalPages(companyPage.getTotalPages())
                .first(companyPage.isFirst())
                .last(companyPage.isLast())
                .build();
    }

    // Update company
    public CompanyDTO updateCompany(Long id, UpdateCompanyRequest request) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with id: " + id));

        // Update các trường nếu có giá trị
        if (request.companyName() != null) {
            company.setCompanyName(request.companyName());
        }
        if (request.address() != null) {
            company.setAddress(request.address());
        }
        if (request.provinceCode() != null) {
            company.setProvinceCode(request.provinceCode());
        }
        if (request.avatarUrl() != null) {
            company.setAvatarUrl(request.avatarUrl());
        }
        if (request.website() != null) {
            company.setWebsite(request.website());
        }
        if (request.phone() != null) {
            company.setPhone(request.phone());
        }
        if (request.email() != null) {
            company.setEmail(request.email());
        }
        if (request.description() != null) {
            company.setDescription(request.description());
        }
        if (request.industry() != null) {
            company.setIndustry(request.industry());
        }
        if (request.companySizeEnum() != null) {
            company.setCompanySizeEnum(request.companySizeEnum());
        }

        Company updatedCompany = companyRepository.save(company);
        return companyMapper.toDTO(updatedCompany);
    }

    // Delete company
    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with id: " + id));
        companyRepository.delete(company);
    }
}

