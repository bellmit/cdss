package com.jhmk.cloudentity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.jhmk.cloudentity"})
public class CloudEntityApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudEntityApplication.class, args);
	}
}
