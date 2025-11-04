package com.fastjob.fastjob_backend.controller.address;

import com.fastjob.fastjob_backend.dto.response.ApiResponse;
import com.fastjob.fastjob_backend.dto.response.WardDTO;
import com.fastjob.fastjob_backend.entity.User;
import com.fastjob.fastjob_backend.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wards")

public class WardController {

  @Autowired
  WardService wardService;

  @GetMapping("/{provinceCode}")
  public ApiResponse<List<WardDTO>> getWardsByProvinceCode(
      @AuthenticationPrincipal User user,
          @RequestParam(defaultValue = "all")
          String search,
          @PathVariable
          String provinceCode) {
    List<WardDTO> wards = wardService.getByProvinceCodeWithSearch(provinceCode, search);
    return ApiResponse.success(wards);
  }
}
