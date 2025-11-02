package com.fastjob.fastjob_backend.dto.response;

import lombok.Builder;

@Builder
public class AuthResponse {
    private String token;
    private String refreshToken;
    private UserDTO user;
}
