package com.fastjob.fastjob_backend.security;

import com.fastjob.fastjob_backend.dto.request.RefreshTokenRequest;
import com.fastjob.fastjob_backend.dto.response.AuthResponse;
import com.fastjob.fastjob_backend.dto.response.UserDTO;
import com.fastjob.fastjob_backend.entity.User;
import com.fastjob.fastjob_backend.entity.UserSession;
import com.fastjob.fastjob_backend.repository.UserRepository;
import com.fastjob.fastjob_backend.repository.UserSessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class JwtService {
    private final String SECRET_KEY = "3122f08a8afa14e4deccff2e91167c28";

    @Autowired
    UserSessionRepository userSessionRepository;

    @Autowired
    UserRepository userRepository;

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId())
                .claim("userType", "USER")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1 day
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }


    public String generateRefreshToken(User user) {
        String refreshToken = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId())
                .claim("userType", "USER")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 7 ngày
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
        UserSession userSession = UserSession
                .builder()
                .userId(user.getId())
                .refreshToken(refreshToken)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(7))
                .build();

        userSessionRepository.save(userSession);
        return refreshToken;
    }

    public Long extractUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("userId", Long.class);
    }

    public String extractUsername (String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Date extractExpiration(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    public boolean isTokenValid(String token, User userDetails) {
        return extractUserId(token).equals(userDetails.getId()) &&
                !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {

        UserSession userSession = userSessionRepository.findByRefreshToken(request.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Refresh token không hợp lệ"));


        if (!userSession.getIsActive() || userSession.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token đã hết hạn hoặc không hợp lệ");
        }


        User user = userRepository.findById(userSession.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        userSessionRepository.delete(userSession);
        UserDTO userDTO = UserDTO.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
        return AuthResponse.builder()
                .token(generateToken(user))
                .refreshToken(generateRefreshToken(user))
                .user(userDTO)
                .build();
    }


    public User getUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            Long userId = extractUserId(token);
            return userRepository.findById(userId)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }
        throw new RuntimeException("Token không hợp lệ");
    }

    public String extractRole(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userType", String.class);
    }

    public String extractUserType(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userType", String.class);
    }


}
