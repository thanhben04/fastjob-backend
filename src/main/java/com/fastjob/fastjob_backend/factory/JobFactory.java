package com.fastjob.fastjob_backend.factory;

import com.fastjob.fastjob_backend.constant.*;
import com.fastjob.fastjob_backend.entity.Company;
import com.fastjob.fastjob_backend.entity.Job;
import com.fastjob.fastjob_backend.entity.Province;
import com.fastjob.fastjob_backend.repository.CompanyRepository;
import com.fastjob.fastjob_backend.repository.JobRepository;
import com.fastjob.fastjob_backend.repository.ProvinceRepository;
import com.github.javafaker.Faker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.Random;

@Component
public class JobFactory {

    private final Faker faker;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ProvinceRepository provinceRepository;



    public JobFactory() {
        // Locale Vietnamese
        this.faker = new Faker(new Locale("vi"));
    }

    public Job createJob() {
        // ✅ Random 1 company từ DB
        long count = companyRepository.count();
        Company randomCompany = companyRepository.findAll().get(
                new Random().nextInt((int) count));

        long count1 = provinceRepository.count();
        Province randomProvince = provinceRepository.findAll().get(
                new Random().nextInt((int) count1));
        return Job.builder()
                .title(faker.job().title())
                .requirement(faker.lorem().paragraph(50))
                .description(faker.lorem().paragraph(50))
                .benefit(faker.lorem().paragraph(50))
                .minSalary(faker.number().randomDouble(2, 1000000, 10000000))
                .maxSalary(faker.number().randomDouble(2, 1000000, 10000000))
                .address(faker.address().fullAddress())
                .province(randomProvince)
//                .provinceCode("01")
                .experienceLevelEnum(
                        ExperienceLevelEnum.values()[new Random().nextInt(ExperienceLevelEnum.values().length)])
                .educationLevelEnum(
                        EducationLevelEnum.values()[new Random().nextInt(EducationLevelEnum.values().length)])
                .jobLevel(JobLevelEnum.values()[new Random().nextInt(JobLevelEnum.values().length)])
                .quantity(faker.number().numberBetween(1, 10))
                .workType(WorkTypeEnum.values()[new Random().nextInt(WorkTypeEnum.values().length)])
                .expirationDate(
                        faker.date().future(10, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .company(randomCompany)
                .jobType(JobTypeEnum.values()[new Random().nextInt(JobTypeEnum.values().length)])
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    // create and persist
    public Job createAndSave(JobRepository repo) {
        Job u;
        int tries = 0;
        do {
            u = createJob();
            tries++;
        } while (repo.existsByTitle(u.getTitle()) && tries < 5);
        return repo.save(u);
    }

}
