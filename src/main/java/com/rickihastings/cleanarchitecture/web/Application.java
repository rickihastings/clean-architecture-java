package com.rickihastings.cleanarchitecture.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.rickihastings.cleanarchitecture.infrastructure.repositories")
@EntityScan("com.rickihastings.cleanarchitecture.domain")
@SpringBootApplication(scanBasePackages = "com.rickihastings.cleanarchitecture")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
