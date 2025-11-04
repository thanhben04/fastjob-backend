package com.fastjob.fastjob_backend.controller;

import com.fastjob.fastjob_backend.dto.request.AuthRequest;
import com.fastjob.fastjob_backend.dto.request.ChangePasswordRequest;
import com.fastjob.fastjob_backend.dto.response.ApiResponse;
import com.fastjob.fastjob_backend.dto.response.AuthResponse;
import com.fastjob.fastjob_backend.entity.User;
import com.fastjob.fastjob_backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ApiResponse<AuthResponse> authenticate(@RequestBody AuthRequest authRequest) {
        AuthResponse response = authService.authenticate(authRequest);

        return ApiResponse.success(response);
    }

    @PostMapping("/register")
    public ApiResponse<AuthResponse> register (@RequestBody AuthRequest authRequest) {
        AuthResponse response = authService.register(authRequest);

        return ApiResponse.success(response);
    }

    @PatchMapping
    public ApiResponse<AuthResponse> refreshToken (@RequestParam("refreshToken") String refreshToken) {
        AuthResponse response = authService.refreshToken(refreshToken);

        return ApiResponse.success(response);
    }

    @PatchMapping("/change-password")
    public ApiResponse<AuthResponse> changePassword (@AuthenticationPrincipal User user, @RequestBody ChangePasswordRequest request) {

        AuthResponse response = authService.changePassword(request, user.getId());

        return ApiResponse.success(response);
    }

    @GetMapping("/bypass/{email}")
    public ApiResponse<AuthResponse> bypass (@PathVariable String email) {
        AuthResponse response = authService.bypass(email);

        return ApiResponse.success(response);
    }


}
