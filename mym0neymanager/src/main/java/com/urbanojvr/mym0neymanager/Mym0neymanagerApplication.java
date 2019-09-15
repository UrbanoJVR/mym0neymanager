package com.urbanojvr.mym0neymanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Mym0neymanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Mym0neymanagerApplication.class, args);
	}

}
