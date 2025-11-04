package com.fastjob.fastjob_backend.service;

import com.fastjob.fastjob_backend.Util.SpecificationUtil;
import com.fastjob.fastjob_backend.dto.request.job.CreateJobRequest;
import com.fastjob.fastjob_backend.dto.request.job.UpdateJobRequest;
import com.fastjob.fastjob_backend.dto.response.PageResponse;
import com.fastjob.fastjob_backend.dto.response.job.JobResponse;
import com.fastjob.fastjob_backend.entity.Company;
import com.fastjob.fastjob_backend.entity.Job;
import com.fastjob.fastjob_backend.exception.CompanyNotFoundException;
import com.fastjob.fastjob_backend.exception.JobNotFoundException;
import com.fastjob.fastjob_backend.repository.CompanyRepository;
import com.fastjob.fastjob_backend.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {
    @Autowired
    JobRepository jobRepository;

    @Autowired
    CompanyRepository companyRepository;

    // Create job
    public JobResponse createJob(CreateJobRequest request) {
        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with id: " + request.companyId()));

        Job job = Job.builder()
                .title(request.title())
                .requirement(request.requirement())
                .description(request.description())
                .benefit(request.benefit())
                .minSalary(request.minSalary())
                .maxSalary(request.maxSalary())
                .address(request.address())
//                .provinceCode(request.provinceCode())
                .educationLevelEnum(request.educationLevelEnum())
                .jobLevel(request.jobLevel())
                .quantity(request.quantity())
                .workType(request.workType())
                .expirationDate(request.expirationDate())
                .company(company)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        Job savedJob = jobRepository.save(job);
        return JobResponse.fromEntity(savedJob);
    }

    // Get job by id
    public JobResponse getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException("Job not found with id: " + id));
        return JobResponse.fromEntity(job);
    }

    // Get all jobs
    public List<JobResponse> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream()
                .map(JobResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // Get jobs with pagination and search
    public PageResponse<JobResponse> getJobsWithPagination(
            String search,
            String provinceCode,
            Long companyId,
            String workType,
            String jobLevel,
            Double minSalary,
            Double maxSalary,
            String sortBy,
            String sortDir,
            int page,
            int size) {
        
        // Tạo Specification từ các tham số tìm kiếm và lọc
        Specification<Job> spec = SpecificationUtil.buildJobSpecification(
                search, provinceCode, companyId, workType, jobLevel, minSalary, maxSalary, sortBy, sortDir);

        // Tạo Pageable với phân trang và sắp xếp
        Pageable pageable = PageRequest.of(page, size);

        // Thực hiện truy vấn với Specification và Pageable
        Page<Job> jobPage = jobRepository.findAll(spec, pageable);

        // Chuyển đổi Page<Job> sang PageResponse<JobResponse>
        List<JobResponse> jobResponses = jobPage.getContent().stream()
                .map(JobResponse::fromEntity)
                .collect(Collectors.toList());

        return PageResponse.<JobResponse>builder()
                .content(jobResponses)
                .page(jobPage.getNumber())
                .size(jobPage.getSize())
                .totalElements(jobPage.getTotalElements())
                .totalPages(jobPage.getTotalPages())
                .first(jobPage.isFirst())
                .last(jobPage.isLast())
                .build();
    }

    // Update job
    public JobResponse updateJob(Long id, UpdateJobRequest request) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException("Job not found with id: " + id));

        // Update các trường nếu có giá trị
        if (request.title() != null) {
            job.setTitle(request.title());
        }
        if (request.requirement() != null) {
            job.setRequirement(request.requirement());
        }
        if (request.description() != null) {
            job.setDescription(request.description());
        }
        if (request.benefit() != null) {
            job.setBenefit(request.benefit());
        }
        if (request.minSalary() != null) {
            job.setMinSalary(request.minSalary());
        }
        if (request.maxSalary() != null) {
            job.setMaxSalary(request.maxSalary());
        }
        if (request.address() != null) {
            job.setAddress(request.address());
        }
        if (request.provinceCode() != null) {
//            job.setProvinceCode(request.provinceCode());
        }

        if (request.educationLevelEnum() != null) {
            job.setEducationLevelEnum(request.educationLevelEnum());
        }
        if (request.jobLevel() != null) {
            job.setJobLevel(request.jobLevel());
        }
        if (request.quantity() != null) {
            job.setQuantity(request.quantity());
        }
        if (request.workType() != null) {
            job.setWorkType(request.workType());
        }
        if (request.expirationDate() != null) {
            job.setExpirationDate(request.expirationDate());
        }
        if (request.companyId() != null) {
            Company company = companyRepository.findById(request.companyId())
                    .orElseThrow(() -> new CompanyNotFoundException("Company not found with id: " + request.companyId()));
            job.setCompany(company);
        }

        job.setUpdatedAt(LocalDateTime.now());
        Job updatedJob = jobRepository.save(job);
        return JobResponse.fromEntity(updatedJob);
    }

    // Delete job
    public void deleteJob(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException("Job not found with id: " + id));
        jobRepository.delete(job);
    }
}
