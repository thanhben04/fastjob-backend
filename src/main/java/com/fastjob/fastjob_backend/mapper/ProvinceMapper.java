package com.fastjob.fastjob_backend.mapper;

import com.fastjob.fastjob_backend.dto.response.ProvinceDTO;
import com.fastjob.fastjob_backend.entity.Province;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProvinceMapper {

    public ProvinceDTO toDTO(Province province) {
        if (province == null) {
            return null;
        }

        return ProvinceDTO.builder()
                .id(province.getId())
                .provinceCode(province.getProvinceCode())
                .name(province.getName())
                .shortName(province.getShortName())
                .code(province.getCode())
                .placeType(province.getPlaceType())
                .country(province.getCountry())
                .createdAt(province.getCreatedAt())
                .updatedAt(province.getUpdatedAt())
                .build();
    }

    public List<ProvinceDTO> toProvinceDTOs(List<Province> provinces) {
        if (provinces == null) {
            return null;
        }

        return provinces.stream()
                .map(this::toDTO)
                .toList();
    }
}
