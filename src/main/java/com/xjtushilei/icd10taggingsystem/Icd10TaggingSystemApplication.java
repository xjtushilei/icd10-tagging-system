package com.xjtushilei.icd10taggingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class Icd10TaggingSystemApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Icd10TaggingSystemApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Icd10TaggingSystemApplication.class, args);
    }
}
