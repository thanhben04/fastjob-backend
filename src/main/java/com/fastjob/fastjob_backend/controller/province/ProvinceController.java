package com.fastjob.fastjob_backend.controller.province;

import com.fastjob.fastjob_backend.dto.request.province.CreateProvinceRequest;
import com.fastjob.fastjob_backend.dto.request.province.UpdateProvinceRequest;
import com.fastjob.fastjob_backend.dto.response.ApiResponse;
import com.fastjob.fastjob_backend.dto.response.PageResponse;
import com.fastjob.fastjob_backend.dto.response.ProvinceDTO;
import com.fastjob.fastjob_backend.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/provinces")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    // Create province
    @PostMapping
    public ApiResponse<ProvinceDTO> createProvince(@RequestBody CreateProvinceRequest request) {
        ProvinceDTO province = provinceService.createProvince(request);
        return ApiResponse.success(province, "Province created successfully");
    }

    // Get province by id
    @GetMapping("/{id}")
    public ApiResponse<ProvinceDTO> getProvinceById(@PathVariable Long id) {
        ProvinceDTO province = provinceService.getProvinceById(id);
        return ApiResponse.success(province);
    }

    // Get province by provinceCode
    @GetMapping("/code/{provinceCode}")
    public ApiResponse<ProvinceDTO> getProvinceByCode(@PathVariable String provinceCode) {
        ProvinceDTO province = provinceService.getProvinceByCode(provinceCode);
        return ApiResponse.success(province);
    }

    // Get all provinces (backward compatibility)
    @GetMapping("/all")
    public ApiResponse<List<ProvinceDTO>> getAllProvinces(
            @RequestParam(required = false) String search) {
        List<ProvinceDTO> provinces = provinceService.getAllProvinces(search);
        return ApiResponse.success(provinces);
    }

    // Get provinces with pagination and search
    @GetMapping
    public ApiResponse<PageResponse<ProvinceDTO>> getProvincesWithPagination(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String placeType,
            @RequestParam(required = false) String provinceCode,
            @RequestParam(required = false) String jobType,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        
        PageResponse<ProvinceDTO> pageResponse = provinceService.getProvincesWithPagination(
                search, country, placeType, provinceCode, jobType, sortBy, sortDir, page, size);
        return ApiResponse.success(pageResponse);
    }

    // Update province
    @PutMapping("/{id}")
    public ApiResponse<ProvinceDTO> updateProvince(
            @PathVariable Long id,
            @RequestBody UpdateProvinceRequest request
    ) {
        ProvinceDTO province = provinceService.updateProvince(id, request);
        return ApiResponse.success(province, "Province updated successfully");
    }

    // Delete province
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProvince(@PathVariable Long id) {
        provinceService.deleteProvince(id);
        return ApiResponse.success(null, "Province deleted successfully");
    }
}
