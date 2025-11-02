package com.fastjob.fastjob_backend.service;

import com.fastjob.fastjob_backend.entity.User;
import com.fastjob.fastjob_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("unifiedUserDetailsService")
@RequiredArgsConstructor
public class UnifiedUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        // Identifier format: "user:userId" hoặc "admin:adminId"
        String[] parts = identifier.split(":");
        if (parts.length != 2) {
            throw new UsernameNotFoundException("Invalid identifier format: " + identifier);
        }

        String type = parts[0];
        String id = parts[1];

        switch (type) {
            case "USER":
                return loadUser(Long.valueOf(id));
            default:
                throw new UsernameNotFoundException("Unknown user type: " + type);
        }
    }

    private UserDetails loadUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));

        return User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}