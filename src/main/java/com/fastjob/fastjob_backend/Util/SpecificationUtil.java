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

}
