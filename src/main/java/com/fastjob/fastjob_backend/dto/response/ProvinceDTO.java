package com.fastjob.fastjob_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProvinceDTO {
    private Long id;
    private String provinceCode;
    private String name;
    private String shortName;
    private String code;
    private String placeType;
    private String country;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
