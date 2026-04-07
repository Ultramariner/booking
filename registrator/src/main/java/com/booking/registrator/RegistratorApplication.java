package com.booking.registrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.booking.feignclients")
@EnableJpaRepositories(basePackages = "com.booking.commondb")
@EntityScan(basePackages = "com.booking.commondb")
public class RegistratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistratorApplication.class, args);
	}

}
