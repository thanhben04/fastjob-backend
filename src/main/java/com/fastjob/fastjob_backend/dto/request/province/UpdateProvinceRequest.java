package com.fastjob.fastjob_backend.dto.request.province;

public record UpdateProvinceRequest(
        String provinceCode,
        String name,
        String shortName,
        String code,
        String placeType,
        String country
) {}

