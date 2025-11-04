package com.fastjob.fastjob_backend.controller.company;

import com.fastjob.fastjob_backend.dto.request.company.CreateCompanyRequest;
import com.fastjob.fastjob_backend.dto.request.company.UpdateCompanyRequest;
import com.fastjob.fastjob_backend.dto.response.ApiResponse;
import com.fastjob.fastjob_backend.dto.response.CompanyDTO;
import com.fastjob.fastjob_backend.dto.response.PageResponse;
import com.fastjob.fastjob_backend.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    // Create company
    @PostMapping
    public ApiResponse<CompanyDTO> createCompany(@RequestBody CreateCompanyRequest request) {
        CompanyDTO company = companyService.createCompany(request);
        return ApiResponse.success(company, "Company created successfully");
    }

    // Get company by id
    @GetMapping("/{id}")
    public ApiResponse<CompanyDTO> getCompanyById(@PathVariable Long id) {
        CompanyDTO company = companyService.getCompanyById(id);
        return ApiResponse.success(company);
    }

    // Get companies with pagination and search
    @GetMapping
    public ApiResponse<PageResponse<CompanyDTO>> getCompaniesWithPagination(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String provinceCode,
            @RequestParam(required = false) String industry,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortDir,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        
        PageResponse<CompanyDTO> pageResponse = companyService.getCompaniesWithPagination(
                search, provinceCode, industry, sortBy, sortDir, page, size);
        return ApiResponse.success(pageResponse);
    }

    // Update company
    @PutMapping("/{id}")
    public ApiResponse<CompanyDTO> updateCompany(
            @PathVariable Long id,
            @RequestBody UpdateCompanyRequest request
    ) {
        CompanyDTO company = companyService.updateCompany(id, request);
        return ApiResponse.success(company, "Company updated successfully");
    }

    // Delete company
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ApiResponse.success(null, "Company deleted successfully");
    }
}

