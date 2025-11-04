package com.fastjob.fastjob_backend.mapper;

import com.fastjob.fastjob_backend.dto.response.UserDTO;
import com.fastjob.fastjob_backend.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO (User user) {
        if (user == null) {
            return null;
        }

        return UserDTO.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

}
