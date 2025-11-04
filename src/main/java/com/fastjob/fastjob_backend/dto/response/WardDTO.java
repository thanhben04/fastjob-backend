package com.fastjob.fastjob_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WardDTO {

    private Long id;

    private String wardCode;

    private String provinceCode;

    private String oldWardName;

    private String newWardName;
}