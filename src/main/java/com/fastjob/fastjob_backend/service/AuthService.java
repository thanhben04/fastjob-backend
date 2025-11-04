package com.fastjob.fastjob_backend.service;

import com.fastjob.fastjob_backend.dto.request.AuthRequest;
import com.fastjob.fastjob_backend.dto.request.ChangePasswordRequest;
import com.fastjob.fastjob_backend.dto.response.AuthResponse;
import com.fastjob.fastjob_backend.entity.User;
import com.fastjob.fastjob_backend.exception.AlreadyExistsException;
import com.fastjob.fastjob_backend.exception.InvalidCredentialsException;
import com.fastjob.fastjob_backend.exception.UserNotFoundException;
import com.fastjob.fastjob_backend.mapper.UserMapper;
import com.fastjob.fastjob_backend.repository.UserRepository;
import com.fastjob.fastjob_backend.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserMapper userMapper;

    public AuthResponse authenticate(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            throw new UserNotFoundException();
        }

        boolean isMatched = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!isMatched) {
            throw new InvalidCredentialsException("Sai tài khoản hoặc mật khẩu");
        }

        return AuthResponse.builder()
                .token(jwtService.generateToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .user(userMapper.toDTO(user))
                .build();
    }

    public AuthResponse register(AuthRequest request) {
        User existingUser = userRepository.findByEmail(request.getEmail())
                .orElse(null);
        if (existingUser != null) {
            throw new AlreadyExistsException("Email này đã được sử dụng");
        }

        User newUser = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(newUser);

        return AuthResponse.builder()
                .token(jwtService.generateToken(newUser))
                .refreshToken(jwtService.generateRefreshToken(newUser))
                .user(userMapper.toDTO(newUser))
                .build();
    }

    public AuthResponse refreshToken(String refreshToken) {
        Long userId = jwtService.extractUserId(refreshToken);
        User user = userRepository.findById(userId)
                .orElse(null);

        if (user == null || !jwtService.isTokenValid(refreshToken, user)) {
            throw new UserNotFoundException();
        }

        return AuthResponse.builder()
                .token(jwtService.generateToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .user(userMapper.toDTO(user))
                .build();
    }

    public AuthResponse changePassword(ChangePasswordRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElse(null);

        if (user == null) {
            throw new UserNotFoundException();
        }

        boolean isMatched = passwordEncoder.matches(request.getOldPassword(), user.getPassword());

        if (!isMatched) {
            throw new InvalidCredentialsException("Mật khẩu cũ không đúng");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.generateToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .user(userMapper.toDTO(user))
                .build();
    }

    public AuthResponse bypass (String email) {
        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null) {
            throw new UserNotFoundException();
        }

        return AuthResponse.builder()
                .token(jwtService.generateToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .user(userMapper.toDTO(user))
                .build();
    }

}
