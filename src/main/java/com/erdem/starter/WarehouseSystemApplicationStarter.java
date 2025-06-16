package com.erdem.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.erdem"})
@EntityScan(basePackages = {"com.erdem"})
@EnableJpaRepositories(basePackages = {"com.erdem"})
@SpringBootApplication
public class WarehouseSystemApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseSystemApplicationStarter.class, args);
	}



}
