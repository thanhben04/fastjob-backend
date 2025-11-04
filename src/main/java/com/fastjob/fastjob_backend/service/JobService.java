package com.fastjob.fastjob_backend.service;

import com.fastjob.fastjob_backend.dto.request.job.CreateJobRequest;
import com.fastjob.fastjob_backend.dto.response.job.JobResponse;
import com.fastjob.fastjob_backend.entity.Company;
import com.fastjob.fastjob_backend.entity.Job;
import com.fastjob.fastjob_backend.repository.CompanyRepository;
import com.fastjob.fastjob_backend.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class JobService {
    @Autowired
    JobRepository jobRepository;

    @Autowired
    CompanyRepository companyRepository;

    // create job service
    public JobResponse createJob(CreateJobRequest request) {
        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Job job = Job.builder()
                .title(request.title())
                .requirement(request.requirement())
                .description(request.description())
                .benefit(request.benefit())
                .minSalary(request.minSalary())
                .maxSalary(request.maxSalary())
                .address(request.address())
                .provinceCode(request.provinceCode())
                .experience(request.experience())
                .educationLevelEnum(request.educationLevelEnum())
                .jobLevel(request.jobLevel())
                .quantity(request.quantity())
                .workType(request.workType())
                .expirationDate(request.expirationDate())
                .company(company)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return JobResponse.fromEntity(job);

    }

    // get jon by id
    public JobResponse getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        return JobResponse.fromEntity(job);
    }

    // get all job search by specification


}
