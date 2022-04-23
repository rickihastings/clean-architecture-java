package com.rickihastings.cleanarchitecture.web;

import com.rickihastings.cleanarchitecture.application.common.interfaces.repositories.IUserRepository;
import com.rickihastings.cleanarchitecture.domain.Role;
import com.rickihastings.cleanarchitecture.domain.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories("com.rickihastings.cleanarchitecture.infrastructure.repositories")
@EntityScan("com.rickihastings.cleanarchitecture.domain")
@SpringBootApplication(scanBasePackages = "com.rickihastings.cleanarchitecture")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(IUserRepository userRepository) {
		return args -> {
			var userRole = new Role();
			userRole.setName("USER");

			var user = new User();
			user.setUsername("admin");
			user.setPassword("admin");
			user.setFirstName("Admin");
			user.setLastName("User");
			user.setIsActive(true);
			user.setRoles(List.of(userRole));

			userRepository.save(user);
		};
	}
}
