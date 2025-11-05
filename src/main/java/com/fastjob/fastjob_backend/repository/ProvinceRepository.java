package com.fastjob.fastjob_backend.repository;

import com.fastjob.fastjob_backend.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long>, JpaSpecificationExecutor<Province> {

  @Query("SELECT p FROM Province p WHERE " + "LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) ")
  List<Province> findByNameContainingIgnoreCase(@Param("search") String search);

  Optional<Province> findByProvinceCode(String provinceCode);
  
  boolean existsByProvinceCode(String provinceCode);
}
