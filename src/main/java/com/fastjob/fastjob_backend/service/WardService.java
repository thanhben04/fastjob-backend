package com.fastjob.fastjob_backend.service;

import com.fastjob.fastjob_backend.dto.response.WardDTO;
import com.fastjob.fastjob_backend.entity.Ward;
import com.fastjob.fastjob_backend.entity.WardMapping;
import com.fastjob.fastjob_backend.mapper.WardMapper;
import com.fastjob.fastjob_backend.repository.WardMappingRepository;
import com.fastjob.fastjob_backend.repository.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WardService {

    @Autowired
    WardRepository wardRepository;
    @Autowired
    WardMappingRepository wardMappingRepository;
    @Autowired
    WardMapper wardMapper;

    public List<WardDTO> getByProvinceCode(String provinceCode) {
        List<Ward> wards = wardRepository.findByProvinceCode(provinceCode);
        return mapToWardDTOs(wards);
    }

    public List<WardDTO> getByProvinceCodeWithSearch(String provinceCode, String searchTerm) {
        List<Ward> wards;

        boolean isSearchEmpty = (searchTerm == null || searchTerm.trim().isEmpty() || "all".equalsIgnoreCase(searchTerm));

        if (isSearchEmpty) {
            wards = wardRepository.findByProvinceCode(provinceCode);
        } else {
            String keyword = searchTerm.trim();

            // 1. Tìm kiếm trực tiếp trong bảng Ward
            Set<Ward> matchedWards = new HashSet<>(wardRepository.findByProvinceCodeAndNameContainingIgnoreCase(provinceCode, keyword));

            // 2. Tìm kiếm tên cũ/mới trong bảng mapping
            List<WardMapping> wardMappings = wardMappingRepository.findByOldWardNameContainingOrNewWardNameContaining(keyword);

            Set<String> wardCodesFromMapping = wardMappings.stream()
                    .map(WardMapping::getOldWardCode)
                    .collect(Collectors.toSet());

            // 3. Lọc các ward trong cùng tỉnh có mã nằm trong mapping
            List<Ward> mappedWards = wardRepository.findByProvinceCode(provinceCode).stream()
                    .filter(ward -> wardCodesFromMapping.contains(ward.getWardCode()))
                    .toList();

            // Gộp kết quả
            matchedWards.addAll(mappedWards);
            wards = new ArrayList<>(matchedWards);
        }

        return mapToWardDTOs(wards);
    }

    private List<WardDTO> mapToWardDTOs(List<Ward> wards) {
        List<WardDTO> wardDTOs = wardMapper.toProvinceDTOs(wards);

        for (WardDTO wardDTO : wardDTOs) {
            WardMapping wardMapping = wardMappingRepository.findByWardCode(wardDTO.getWardCode()).stream().findFirst().orElse(null);
            if (wardMapping != null) {
                wardDTO.setOldWardName(wardMapping.getOldWardName());
                wardDTO.setNewWardName(wardMapping.getNewWardName());
            }
        }

        return wardDTOs;
    }



}
