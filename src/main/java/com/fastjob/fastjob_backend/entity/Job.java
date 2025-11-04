package com.fastjob.fastjob_backend.entity;

import com.fastjob.fastjob_backend.constant.EducationLevelEnum;
import com.fastjob.fastjob_backend.constant.JobLevelEnum;
import com.fastjob.fastjob_backend.constant.WorkTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String requirement;

    private String description;

    private String benefit;

    private Double minSalary;

    private Double maxSalary;

    private String address;

    private String provinceCode;

    private Integer experience; // in years


    @Enumerated(EnumType.STRING) // Lưu dạng text thay vì số
    @Column(name = "education_level", nullable = true)
    private EducationLevelEnum educationLevelEnum;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_level", nullable = false)
    private JobLevelEnum jobLevel;

    // số lượng tuyển
    private Integer quantity;

    // hình thức làm việc (Full-time, Part-time, Internship, etc.)
    @Enumerated(EnumType.STRING)
    @Column(name = "work_type", nullable = false)
    private WorkTypeEnum workType;


    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
