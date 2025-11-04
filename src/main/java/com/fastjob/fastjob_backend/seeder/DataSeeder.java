package com.fastjob.fastjob_backend.seeder;

import com.fastjob.fastjob_backend.entity.Company;
import com.fastjob.fastjob_backend.entity.Job;
import com.fastjob.fastjob_backend.factory.CompanyFactory;
import com.fastjob.fastjob_backend.factory.JobFactory;
import com.fastjob.fastjob_backend.repository.CompanyRepository;
import com.fastjob.fastjob_backend.repository.JobRepository;
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

    private final JobFactory jobFactory;
    private final JobRepository jobRepository;

//    @Bean
//    CommandLineRunner companySeedRunner() {
//        return args -> {
//            if (companyRepository.count() > 0) {
//                System.out.println("Seed skipped - company exist");
//                return;
//            }
//
//            List<Company> users = IntStream.range(0, 50)
//                    .mapToObj(i -> companyFactory.createAndSave(companyRepository))
//                    .collect(Collectors.toList());
//
//            companyRepository.saveAll(users);
//            System.out.println("Seeded " + users.size() + " users");
//        };
//    }

    @Bean
    CommandLineRunner jobSeedRunner() {
        return args -> {
            if (jobRepository.count() > 0) {
                System.out.println("Seed skipped - job exist");
                return;
            }

            List<Job> jobs = IntStream.range(0, 50)
                    .mapToObj(i -> jobFactory.createAndSave(jobRepository))
                    .collect(Collectors.toList());

            jobRepository.saveAll(jobs);
            System.out.println("Seeded " + jobs.size() + " jobs");
        };
    }
}
