package com.booking.generator;

import com.booking.feignclients.clients.RegistratorClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableFeignClients(clients = {RegistratorClient.class})
@EnableJpaRepositories(basePackages = "com.booking.commondb")
@EntityScan(basePackages = "com.booking.commondb")
public class GeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeneratorApplication.class, args);
	}

}
