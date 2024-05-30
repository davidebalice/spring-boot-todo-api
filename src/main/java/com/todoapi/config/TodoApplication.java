package com.todoapi.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableScheduling
@EntityScan(basePackages = { "com.todoapi.model" })
@ComponentScan({ "com.todoapi" })
@EnableJpaRepositories(basePackages = "com.todoapi.repository")
@OpenAPIDefinition(
	info = @Info(
		title = "Spring Boot todo rest api template",
		description = "Rest application for todo app. Developed by Davide Balice",
		version = "v1.0.0",
		contact = @Contact(
				name = "Davide Balice",
				email = "davide.balice@gmail.com",
				url = "https://www.davidebalice.dev"
		)
	)
)

public class TodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}
}