package com.saae.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SaaeBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaaeBackendApplication.class, args);
	}

}
