package com.fastjob.fastjob_backend.controller.job;

import com.fastjob.fastjob_backend.dto.request.job.CreateJobRequest;
import com.fastjob.fastjob_backend.dto.request.job.UpdateJobRequest;
import com.fastjob.fastjob_backend.dto.response.ApiResponse;
import com.fastjob.fastjob_backend.dto.response.PageResponse;
import com.fastjob.fastjob_backend.dto.response.job.JobResponse;
import com.fastjob.fastjob_backend.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    // Create job
    @PostMapping
    public ApiResponse<JobResponse> createJob(@RequestBody CreateJobRequest request) {
        JobResponse job = jobService.createJob(request);
        return ApiResponse.success(job, "Job created successfully");
    }

    // Get job by id
    @GetMapping("/{id}")
    public ApiResponse<JobResponse> getJobById(@PathVariable Long id) {
        JobResponse job = jobService.getJobById(id);
        return ApiResponse.success(job);
    }

    // Get all jobs
    @GetMapping("/all")
    public ApiResponse<List<JobResponse>> getAllJobs() {
        List<JobResponse> jobs = jobService.getAllJobs();
        return ApiResponse.success(jobs);
    }

    // Get jobs with pagination and search
    @GetMapping
    public ApiResponse<PageResponse<JobResponse>> getJobsWithPagination(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String provinceCode,
            @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) String workType,
            @RequestParam(required = false) String jobLevel,
            @RequestParam(required = false) Double minSalary,
            @RequestParam(required = false) Double maxSalary,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortDir,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        
        PageResponse<JobResponse> pageResponse = jobService.getJobsWithPagination(
                search, provinceCode, companyId, workType, jobLevel, minSalary, maxSalary, sortBy, sortDir, page, size);
        return ApiResponse.success(pageResponse);
    }

    // Update job
    @PutMapping("/{id}")
    public ApiResponse<JobResponse> updateJob(
            @PathVariable Long id,
            @RequestBody UpdateJobRequest request
    ) {
        JobResponse job = jobService.updateJob(id, request);
        return ApiResponse.success(job, "Job updated successfully");
    }

    // Delete job
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ApiResponse.success(null, "Job deleted successfully");
    }
}
