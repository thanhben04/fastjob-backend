package com.fastjob.fastjob_backend.factory;

import com.fastjob.fastjob_backend.constant.CompanySizeEnum;
import com.fastjob.fastjob_backend.entity.Company;
import com.fastjob.fastjob_backend.repository.CompanyRepository;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Random;

@Component
public class CompanyFactory {

    private final Faker faker;

    public CompanyFactory() {
        // Locale Vietnamese
        this.faker = new Faker(new Locale("vi"));
    }

    public Company createCompany() {
        return Company.builder()
                .companyName(faker.company().name())
                .address(faker.address().fullAddress())
                .provinceCode("01")
                .avatarUrl(faker.avatar().image())
                .phone(faker.phoneNumber().phoneNumber())
                .email(faker.internet().emailAddress())
                .website(faker.internet().url())
                .description(faker.lorem().paragraph(100))
                .industry(faker.company().industry())
                .companySizeEnum(CompanySizeEnum.values()[new Random().nextInt(CompanySizeEnum.values().length)])
                .build();
    }


    // create and persist
    public Company createAndSave(CompanyRepository repo) {
        Company u;
        int tries = 0;
        do {
            u = createCompany();
            tries++;
        } while (repo.existsByEmail(u.getEmail()) && tries < 5);
        return repo.save(u);
    }

}
