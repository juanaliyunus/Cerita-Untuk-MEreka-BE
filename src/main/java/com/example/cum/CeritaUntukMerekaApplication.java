package com.example.cum;

import com.example.cum.entity.AppUser;
import com.example.cum.security.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class CeritaUntukMerekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CeritaUntukMerekaApplication.class, args);
	}

}
