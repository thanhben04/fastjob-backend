package com.fastjob.fastjob_backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthResponse {
    private String token;
    private String refreshToken;
    private UserDTO user;
}
