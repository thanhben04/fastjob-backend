package com.fastjob.fastjob_backend.controller.address;

import com.fastjob.fastjob_backend.dto.response.ApiResponse;
import com.fastjob.fastjob_backend.dto.response.ProvinceDTO;
import com.fastjob.fastjob_backend.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/provinces")
public class ProvinceController {

    @Autowired
    ProvinceService provinceService;

    @GetMapping
    public ApiResponse<List<ProvinceDTO>> getAllProvinces(
            @RequestParam(defaultValue = "all") String search
    ) {
        List<ProvinceDTO> provinces = provinceService.getAllProvinces(search);
        return ApiResponse.success(provinces);
    }

}
