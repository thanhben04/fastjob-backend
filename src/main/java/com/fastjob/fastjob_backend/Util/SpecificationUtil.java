package com.fastjob.fastjob_backend.Util;

import com.fastjob.fastjob_backend.entity.Company;
import com.fastjob.fastjob_backend.entity.Job;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/**
 * Lớp tiện ích để xây dựng các đối tượng {@link Specification} cho việc tạo truy vấn động với JPA
 * Criteria API.
 *
 * <p>Lớp này chứa các phương thức tĩnh để tạo các điều kiện lọc phức tạp dựa trên các tham số đầu
 * vào như quyền hạn người dùng, từ khóa tìm kiếm, và các bộ lọc khác.
 *
 * @author NatswarChuan
 */
public class SpecificationUtil {
    private SpecificationUtil() {}


    public static Specification<Job> buildProductSpecification(
            String search,
            String searchField,
            String sortBy,
            String sortDir) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (search != null && !search.isEmpty() && searchField != null && !searchField.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(searchField)),
                        "%" + search.toLowerCase() + "%"));
            }


            query.distinct(true);

            if (sortBy != null && !sortBy.isEmpty()) {
                if (sortDir != null && sortDir.equalsIgnoreCase("asc")) {
                    query.orderBy(criteriaBuilder.asc(root.get(sortBy)));
                } else {
                    query.orderBy(criteriaBuilder.desc(root.get(sortBy)));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Xây dựng Specification cho Company với tìm kiếm đa trường và sắp xếp
     *
     * @param search Từ khóa tìm kiếm (tìm trong companyName, email, phone, industry, address)
     * @param provinceCode Lọc theo mã tỉnh/thành phố
     * @param industry Lọc theo lĩnh vực hoạt động
     * @param sortBy Trường để sắp xếp
     * @param sortDir Hướng sắp xếp (asc/desc)
     * @return Specification cho Company
     */
    public static Specification<Company> buildCompanySpecification(
            String search,
            String provinceCode,
            String industry,
            String sortBy,
            String sortDir) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Tìm kiếm đa trường: companyName, email, phone, industry, address
            if (search != null && !search.trim().isEmpty()) {
                String searchPattern = "%" + search.toLowerCase().trim() + "%";
                Predicate companyNamePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("companyName")), searchPattern);
                Predicate emailPredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("email")), searchPattern);
                Predicate phonePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("phone")), searchPattern);
                Predicate industryPredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("industry")), searchPattern);
                Predicate addressPredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("address")), searchPattern);

                predicates.add(criteriaBuilder.or(
                        companyNamePredicate,
                        emailPredicate,
                        phonePredicate,
                        industryPredicate,
                        addressPredicate
                ));
            }

            // Lọc theo provinceCode
            if (provinceCode != null && !provinceCode.trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("provinceCode")),
                        provinceCode.toLowerCase().trim()));
            }

            // Lọc theo industry
            if (industry != null && !industry.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("industry")),
                        "%" + industry.toLowerCase().trim() + "%"));
            }

            query.distinct(true);

            // Sắp xếp
            if (sortBy != null && !sortBy.trim().isEmpty()) {
                if (sortDir != null && sortDir.equalsIgnoreCase("asc")) {
                    query.orderBy(criteriaBuilder.asc(root.get(sortBy)));
                } else {
                    query.orderBy(criteriaBuilder.desc(root.get(sortBy)));
                }
            } else {
                // Mặc định sắp xếp theo id giảm dần
                query.orderBy(criteriaBuilder.desc(root.get("id")));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Xây dựng Specification cho Job với tìm kiếm đa trường và sắp xếp
     *
     * @param search Từ khóa tìm kiếm (tìm trong title, description, requirement, address)
     * @param provinceCode Lọc theo mã tỉnh/thành phố
     * @param companyId Lọc theo ID công ty
     * @param workType Lọc theo hình thức làm việc
     * @param jobLevel Lọc theo cấp độ công việc
     * @param minSalary Lọc theo mức lương tối thiểu
     * @param maxSalary Lọc theo mức lương tối đa
     * @param sortBy Trường để sắp xếp
     * @param sortDir Hướng sắp xếp (asc/desc)
     * @return Specification cho Job
     */
    public static Specification<Job> buildJobSpecification(
            String search,
            String provinceCode,
            Long companyId,
            String workType,
            String jobLevel,
            Double minSalary,
            Double maxSalary,
            String sortBy,
            String sortDir) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Tìm kiếm đa trường: title, description, requirement, address
            if (search != null && !search.trim().isEmpty()) {
                String searchPattern = "%" + search.toLowerCase().trim() + "%";
                Predicate titlePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")), searchPattern);
                Predicate descriptionPredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("description")), searchPattern);
                Predicate requirementPredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("requirement")), searchPattern);
                Predicate addressPredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("address")), searchPattern);

                predicates.add(criteriaBuilder.or(
                        titlePredicate,
                        descriptionPredicate,
                        requirementPredicate,
                        addressPredicate
                ));
            }

            // Lọc theo provinceCode
            if (provinceCode != null && !provinceCode.trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("provinceCode")),
                        provinceCode.toLowerCase().trim()));
            }

            // Lọc theo companyId
            if (companyId != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("company").get("id"), companyId));
            }

            // Lọc theo workType
            if (workType != null && !workType.trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("workType").as(String.class)),
                        workType.toLowerCase().trim()));
            }

            // Lọc theo jobLevel
            if (jobLevel != null && !jobLevel.trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("jobLevel").as(String.class)),
                        jobLevel.toLowerCase().trim()));
            }

            // Lọc theo mức lương tối thiểu
            if (minSalary != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("minSalary"), minSalary));
            }

            // Lọc theo mức lương tối đa
            if (maxSalary != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("maxSalary"), maxSalary));
            }

            query.distinct(true);

            // Sắp xếp
            if (sortBy != null && !sortBy.trim().isEmpty()) {
                if (sortDir != null && sortDir.equalsIgnoreCase("asc")) {
                    query.orderBy(criteriaBuilder.asc(root.get(sortBy)));
                } else {
                    query.orderBy(criteriaBuilder.desc(root.get(sortBy)));
                }
            } else {
                // Mặc định sắp xếp theo createdAt giảm dần
                query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
