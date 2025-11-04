package com.fastjob.fastjob_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "provinces")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "province_code")
    private String provinceCode;
    private String name;
    private String shortName;
    private String code;
    private String placeType;
    private String country;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
