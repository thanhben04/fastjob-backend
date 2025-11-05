package com.fastjob.fastjob_backend.service;

import com.fastjob.fastjob_backend.Util.SpecificationUtil;
import com.fastjob.fastjob_backend.dto.request.province.CreateProvinceRequest;
import com.fastjob.fastjob_backend.dto.request.province.UpdateProvinceRequest;
import com.fastjob.fastjob_backend.dto.response.PageResponse;
import com.fastjob.fastjob_backend.dto.response.ProvinceDTO;
import com.fastjob.fastjob_backend.entity.Province;
import com.fastjob.fastjob_backend.exception.AlreadyExistsException;
import com.fastjob.fastjob_backend.exception.ProvinceNotFoundException;
import com.fastjob.fastjob_backend.mapper.ProvinceMapper;
import com.fastjob.fastjob_backend.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProvinceService {
    @Autowired
    ProvinceRepository provinceRepository;

    @Autowired
    ProvinceMapper provinceMapper;

    // Create province
    public ProvinceDTO createProvince(CreateProvinceRequest request) {
        // Kiểm tra provinceCode đã tồn tại chưa
        if (request.provinceCode() != null && provinceRepository.existsByProvinceCode(request.provinceCode())) {
            throw new AlreadyExistsException("Province with code " + request.provinceCode() + " already exists");
        }

        Province province = Province.builder()
                .provinceCode(request.provinceCode())
                .name(request.name())
                .shortName(request.shortName())
                .code(request.code())
                .placeType(request.placeType())
                .country(request.country())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Province savedProvince = provinceRepository.save(province);
        return provinceMapper.toDTO(savedProvince);
    }

    // Get province by id
    public ProvinceDTO getProvinceById(Long id) {
        Province province = provinceRepository.findById(id)
                .orElseThrow(() -> new ProvinceNotFoundException("Province not found with id: " + id));
        return provinceMapper.toDTO(province);
    }

    // Get province by provinceCode
    public ProvinceDTO getProvinceByCode(String provinceCode) {
        Province province = provinceRepository.findByProvinceCode(provinceCode)
                .orElseThrow(() -> new ProvinceNotFoundException("Province not found with code: " + provinceCode));
        return provinceMapper.toDTO(province);
    }

    // Get all provinces (backward compatibility)
    public List<ProvinceDTO> getAllProvinces(String search) {
        if (search != null && !search.trim().isEmpty() && !"all".equalsIgnoreCase(search)) {
            String searchTerm = search.trim();
            return provinceMapper.toProvinceDTOs(
                    provinceRepository.findByNameContainingIgnoreCase(searchTerm));
        }
        List<Province> provinces = provinceRepository.findAll();
        return provinceMapper.toProvinceDTOs(provinces);
    }

    // Get provinces with pagination and search
    public PageResponse<ProvinceDTO> getProvincesWithPagination(
            String search,
            String country,
            String placeType,
            String provinceCode,
            String jobType,
            String sortBy,
            String sortDir,
            int page,
            int size) {
        
        // Tạo Specification từ các tham số tìm kiếm và lọc
        Specification<Province> spec = SpecificationUtil.buildProvinceSpecification(
                search, country, placeType, provinceCode, jobType, sortBy, sortDir);

        // Tạo Pageable với phân trang và sắp xếp
        Pageable pageable = PageRequest.of(page, size);

        // Thực hiện truy vấn với Specification và Pageable
        Page<Province> provincePage = provinceRepository.findAll(spec, pageable);

        // Chuyển đổi Page<Province> sang PageResponse<ProvinceDTO>
        List<ProvinceDTO> provinceDTOs = provinceMapper.toProvinceDTOs(provincePage.getContent());

        return PageResponse.<ProvinceDTO>builder()
                .content(provinceDTOs)
                .page(provincePage.getNumber())
                .size(provincePage.getSize())
                .totalElements(provincePage.getTotalElements())
                .totalPages(provincePage.getTotalPages())
                .first(provincePage.isFirst())
                .last(provincePage.isLast())
                .build();
    }

    // Update province
    public ProvinceDTO updateProvince(Long id, UpdateProvinceRequest request) {
        Province province = provinceRepository.findById(id)
                .orElseThrow(() -> new ProvinceNotFoundException("Province not found with id: " + id));

        // Kiểm tra provinceCode đã tồn tại chưa (nếu có thay đổi)
        if (request.provinceCode() != null && 
            !request.provinceCode().equals(province.getProvinceCode()) &&
            provinceRepository.existsByProvinceCode(request.provinceCode())) {
            throw new AlreadyExistsException("Province with code " + request.provinceCode() + " already exists");
        }

        // Update các trường nếu có giá trị
        if (request.provinceCode() != null) {
            province.setProvinceCode(request.provinceCode());
        }
        if (request.name() != null) {
            province.setName(request.name());
        }
        if (request.shortName() != null) {
            province.setShortName(request.shortName());
        }
        if (request.code() != null) {
            province.setCode(request.code());
        }
        if (request.placeType() != null) {
            province.setPlaceType(request.placeType());
        }
        if (request.country() != null) {
            province.setCountry(request.country());
        }

        province.setUpdatedAt(LocalDateTime.now());
        Province updatedProvince = provinceRepository.save(province);
        return provinceMapper.toDTO(updatedProvince);
    }

    // Delete province
    public void deleteProvince(Long id) {
        Province province = provinceRepository.findById(id)
                .orElseThrow(() -> new ProvinceNotFoundException("Province not found with id: " + id));
        provinceRepository.delete(province);
    }
}
