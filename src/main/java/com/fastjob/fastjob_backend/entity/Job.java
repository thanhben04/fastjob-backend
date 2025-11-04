package com.fastjob.fastjob_backend.entity;

import com.fastjob.fastjob_backend.constant.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "jobs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "requirement", columnDefinition = "TEXT")
    private String requirement;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "benefit", columnDefinition = "TEXT")
    private String benefit;

    private Double minSalary;

    private Double maxSalary;

    private String address;

//    private String provinceCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "experience_level", nullable = true)
    private ExperienceLevelEnum experienceLevelEnum;


    @Enumerated(EnumType.STRING) // Lưu dạng text thay vì số
    @Column(name = "education_level", nullable = true)
    private EducationLevelEnum educationLevelEnum;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_level", nullable = false)
    private JobLevelEnum jobLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_type", nullable = false)
    private JobTypeEnum jobType;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_code", referencedColumnName = "provinceCode")
    private Province province;
}
