package com.fastjob.fastjob_backend.repository;

import com.fastjob.fastjob_backend.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WardRepository extends JpaRepository<Ward, Long> {
    List<Ward> findByProvinceCode(String provinceCode);
    
    Optional<Ward> findByWardCode(String wardCode);

    @Query("SELECT DISTINCT w FROM Ward w LEFT JOIN WardMapping wm ON w.wardCode = wm.oldWardCode " +
           "WHERE w.provinceCode = ?1 AND (w.name LIKE %?2% OR w.wardCode LIKE %?2% OR wm.oldWardName LIKE %?2% OR wm.newWardName LIKE %?2%)")
    List<Ward> findByProvinceCodeAndNameContainingIgnoreCase(String provinceCode, String searchTerm);

}
