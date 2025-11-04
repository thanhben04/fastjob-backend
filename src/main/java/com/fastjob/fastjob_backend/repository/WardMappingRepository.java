package com.fastjob.fastjob_backend.repository;

import com.fastjob.fastjob_backend.entity.WardMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardMappingRepository extends JpaRepository<WardMapping, Long> {

    @Query(value = "SELECT * FROM ward_mappings WHERE old_ward_code = ?1", nativeQuery = true)
    List<WardMapping> findByWardCode(String wardCode);

    WardMapping findByOldWardCode(String oldWardCode);
    
    @Query("SELECT wm FROM WardMapping wm WHERE wm.oldWardName LIKE %?1% OR wm.newWardName LIKE %?1%")
    List<WardMapping> findByOldWardNameContainingOrNewWardNameContaining(String searchTerm);
}
