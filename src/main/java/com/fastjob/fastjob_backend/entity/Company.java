package com.fastjob.fastjob_backend.entity;

import com.fastjob.fastjob_backend.constant.CompanySizeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "companies")
@Builder
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;

    private String address;

    private String provinceCode;

    private String avatarUrl;

    private String website;

    private String phone;

    private String email;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // lĩnh vực hoạt động
    private String industry;

    // QUY MÔ CÔNG TY
    @Enumerated(EnumType.STRING)
    @Column(name = "company_size", nullable = true)
    private CompanySizeEnum companySizeEnum;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Job> jobs;

}
