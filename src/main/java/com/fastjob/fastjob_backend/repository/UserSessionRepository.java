package com.fastjob.fastjob_backend.repository;

import com.fastjob.fastjob_backend.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, String> {

    Optional<UserSession> findByRefreshToken(String refreshToken);

    List<UserSession> findByUserIdAndIsActiveTrue(Long userId);

    @Modifying
    @Query("UPDATE UserSession s SET s.isActive = false WHERE s.userId = :userId")
    void deactivateAllUserSessions(Long userId);
    //
    @Modifying
    @Query("UPDATE UserSession s SET s.isActive = false WHERE s.userId = :userId")
    void deactivateRefreshTokenByUserID(Long userId);


    @Modifying
    @Query("DELETE FROM UserSession s WHERE s.expiresAt < :now OR s.isActive = false")
    void cleanupExpiredSessions(LocalDateTime now);
}