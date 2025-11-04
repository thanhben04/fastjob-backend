package com.fastjob.fastjob_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ward_mappings")
public class WardMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "old_ward_code")
    private String oldWardCode;
    private String oldWardName;
    private String oldDistrictName;
    private String oldProvinceName;
    private String newWardCode;
    private String newWardName;
    private String newProvinceName;


}
