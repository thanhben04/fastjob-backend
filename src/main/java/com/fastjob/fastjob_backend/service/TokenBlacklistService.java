package com.fastjob.fastjob_backend.service;

import com.fastjob.fastjob_backend.entity.TokenBlacklist;
import com.fastjob.fastjob_backend.repository.TokenBlacklistRepository;
import com.fastjob.fastjob_backend.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenBlacklistService {

    TokenBlacklistRepository tokenBlacklistRepository;
    JwtService jwtService;

    @Transactional
    public void blacklistToken(String token, Long userId, TokenBlacklist.TokenType tokenType) {
        if (isTokenBlacklisted(token)) {
            return;
        }

        LocalDateTime expirationTime = jwtService
                .extractExpiration(token)
                .toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDateTime();

        TokenBlacklist blacklistedToken = TokenBlacklist.builder()
                .token(token)
                .userId(userId)
                .tokenType(tokenType)
                .createdAt(LocalDateTime.now())
                .expiresAt(expirationTime)
                .build();

        tokenBlacklistRepository.save(blacklistedToken);
        log.info("Token blacklisted for user: {}, type: {}", userId, tokenType);
    }

    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklistRepository.existsByToken(token);
    }

    @Transactional
    public void blacklistAllUserTokens(String userId) {
        log.info("Blacklisting all tokens for user: {}", userId);
    }

    @Transactional
    @Async
    public void cleanupExpiredTokens() {
        tokenBlacklistRepository.deleteExpiredTokens(LocalDateTime.now());
        log.info("Cleaned up expired blacklisted tokens");
    }
}