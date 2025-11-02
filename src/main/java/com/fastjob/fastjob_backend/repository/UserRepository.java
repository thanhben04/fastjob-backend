package com.fastjob.fastjob_backend.repository;

import com.fastjob.fastjob_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
