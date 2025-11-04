package com.fastjob.fastjob_backend.controller.job;

import com.fastjob.fastjob_backend.dto.request.job.CreateJobRequest;
import com.fastjob.fastjob_backend.dto.response.ApiResponse;
import com.fastjob.fastjob_backend.dto.response.job.JobResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    // create job
    public ApiResponse<JobResponse> createJob (CreateJobRequest request) {
        return null;
    }

}
