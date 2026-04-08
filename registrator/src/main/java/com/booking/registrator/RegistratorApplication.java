package com.booking.registrator;

import com.booking.feignclients.clients.PaymentClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableFeignClients(clients = {PaymentClient.class})
@EnableJpaRepositories(basePackages = "com.booking.commondb")
@EntityScan(basePackages = "com.booking.commondb")
public class RegistratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistratorApplication.class, args);
	}

}
