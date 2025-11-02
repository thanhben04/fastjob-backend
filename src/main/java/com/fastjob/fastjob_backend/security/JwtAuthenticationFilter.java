package com.fastjob.fastjob_backend.security;

import com.fastjob.fastjob_backend.entity.User;
import com.fastjob.fastjob_backend.service.TokenBlacklistService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    JwtService jwtService;
    UserDetailsService userDetailsService;
    TokenBlacklistService tokenBlacklistService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userType;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        try {
            // Check if token is blacklisted
            if (tokenBlacklistService.isTokenBlacklisted(jwt)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\": \"Token is invalid\"}");
                return;
            }
        } catch (Exception e) {
            System.err.println("Error checking token blacklist: " + e.getMessage());
            e.printStackTrace();
            // Continue without blacklist check if database is down
        }

        try {
            userType = jwtService.extractUserType(jwt);
        } catch (Exception e) {
            System.err.println("Error extracting userType from token: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Invalid token format\"}");
            return;
        }

        if (userType != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails;
                String role = jwtService.extractRole(jwt);
                boolean isTokenValid = false;

                if ("USER".equals(userType)) {
                    Long userId = jwtService.extractUserId(jwt);
                    userDetails = userDetailsService.loadUserByUsername("USER:" + userId);
                    isTokenValid = jwtService.isTokenValid(jwt, (User) userDetails);
                } else {
                    System.err.println("Unknown user type: " + userType);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("{\"error\": \"Unknown user type\"}");
                    return;
                }

                if (isTokenValid) {

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    System.err.println("Token validation failed for " + userType);
                }
            } catch (UsernameNotFoundException e) {
                System.err.println("User/Admin not found: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\": \"User/Admin not found\"}");
                return;
            } catch (Exception e) {
                System.err.println("Error validating token: " + e.getMessage());
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\": \"Token validation failed\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}