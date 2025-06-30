package com.example.background;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.background", "com.neulab.fund"})
@EnableJpaRepositories(basePackages = "com.neulab.fund.repository")
@EntityScan(basePackages = "com.neulab.fund.entity")
public class BackgroundApplication {

	public static void main(String[] args) {
		System.out.println("Starting NEU Fund Advisory Application...");
		SpringApplication.run(BackgroundApplication.class, args);
		System.out.println("Application started successfully!");
	}

}
