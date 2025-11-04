package com.fastjob.fastjob_backend.mapper;

import com.fastjob.fastjob_backend.dto.response.WardDTO;
import com.fastjob.fastjob_backend.entity.Ward;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WardMapper {
    public WardDTO toProvinceDTO(Ward ward) {
        if ( ward == null ) {
            return null;
        }

        WardDTO.WardDTOBuilder wardDTO = WardDTO.builder();

        wardDTO.id( ward.getId() );
        wardDTO.provinceCode( ward.getProvinceCode() );
        wardDTO.wardCode( ward.getWardCode() );

        return wardDTO.build();
    }

    public List<WardDTO> toProvinceDTOs(List<Ward> wards) {
        if ( wards == null ) {
            return null;
        }

        List<WardDTO> list = new ArrayList<WardDTO>( wards.size() );
        for ( Ward ward : wards ) {
            list.add( toProvinceDTO( ward ) );
        }

        return list;
    }
}
