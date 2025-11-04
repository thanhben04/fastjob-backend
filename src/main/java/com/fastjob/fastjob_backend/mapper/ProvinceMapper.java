package com.fastjob.fastjob_backend.mapper;

import com.fastjob.fastjob_backend.dto.response.ProvinceDTO;
import com.fastjob.fastjob_backend.entity.Province;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProvinceMapper {

    public ProvinceDTO toDTO (Province province) {
        if ( province == null ) {
            return null;
        }

        ProvinceDTO provinceDTO = new ProvinceDTO();

        provinceDTO.setId( province.getId() );
        provinceDTO.setName( province.getName() );
        provinceDTO.setProvinceCode( province.getProvinceCode() );

        return provinceDTO;
    }

    public List<ProvinceDTO> toProvinceDTOs (List<Province> provinces) {
        if ( provinces == null ) {
            return null;
        }

        List<ProvinceDTO> list = new java.util.ArrayList<ProvinceDTO>( provinces.size() );
        for ( Province province : provinces ) {
            list.add( toDTO( province ) );
        }

        return list;
    }
}
