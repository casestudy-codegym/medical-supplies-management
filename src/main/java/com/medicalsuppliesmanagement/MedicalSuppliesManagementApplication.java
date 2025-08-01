package com.medicalsuppliesmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class MedicalSuppliesManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalSuppliesManagementApplication.class, args);
    }

}
