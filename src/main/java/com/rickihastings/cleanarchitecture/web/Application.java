package com.rickihastings.cleanarchitecture.web;

import io.jkratz.mediator.core.Mediator;
import io.jkratz.mediator.core.Registry;
import io.jkratz.mediator.spring.SpringMediator;
import io.jkratz.mediator.spring.SpringRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.rickihastings.cleanarchitecture.infrastructure.repositories")
@EntityScan("com.rickihastings.cleanarchitecture.domain")
@SpringBootApplication(scanBasePackages = "com.rickihastings.cleanarchitecture")
public class Application {

	private final ApplicationContext applicationContext;

	@Autowired
	public Application(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Bean
	public Registry registry() {
		return new SpringRegistry(applicationContext);
	}

	@Bean
	public Mediator mediator(Registry registry) {
		return new SpringMediator(registry);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
