package com.fastjob.fastjob_backend.repository;

import com.fastjob.fastjob_backend.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
