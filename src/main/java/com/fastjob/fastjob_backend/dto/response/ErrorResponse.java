package com.fastjob.fastjob_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
public class ErrorResponse {
    private String message;
    private int status;
    private Instant timestamp = Instant.now();

}
