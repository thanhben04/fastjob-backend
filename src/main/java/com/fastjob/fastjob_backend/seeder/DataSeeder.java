package com.fastjob.fastjob_backend.seeder;

import com.fastjob.fastjob_backend.entity.Company;
import com.fastjob.fastjob_backend.factory.CompanyFactory;
import com.fastjob.fastjob_backend.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {

    private final CompanyFactory companyFactory;
    private final CompanyRepository companyRepository;

    @Bean
    CommandLineRunner seedRunner() {
        return args -> {
            if (companyRepository.count() > 0) {
                System.out.println("Seed skipped - users exist");
                return;
            }

            List<Company> users = IntStream.range(0, 50)
                    .mapToObj(i -> companyFactory.createAndSave(companyRepository))
                    .collect(Collectors.toList());

            companyRepository.saveAll(users);
            System.out.println("Seeded " + users.size() + " users");
        };
    }
}
