package com.fastjob.fastjob_backend.service;

import com.fastjob.fastjob_backend.dto.response.ProvinceDTO;
import com.fastjob.fastjob_backend.entity.Province;
import com.fastjob.fastjob_backend.mapper.ProvinceMapper;
import com.fastjob.fastjob_backend.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceService {
    @Autowired
    ProvinceRepository provinceRepository;

    @Autowired
    ProvinceMapper provinceMapper;

    public List<ProvinceDTO> getAllProvinces(String search) {
        if (search != null && !search.trim().isEmpty() && !"all".equalsIgnoreCase(search)) {
            String searchTerm = search.trim();
            return provinceMapper.toProvinceDTOs(
                    provinceRepository.findByNameContainingIgnoreCase(searchTerm));
        }
        List<Province> provinces = provinceRepository.findAll();
        return provinceMapper.toProvinceDTOs(provinces);
    }
}
