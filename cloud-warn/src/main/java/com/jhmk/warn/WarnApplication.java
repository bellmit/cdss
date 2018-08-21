package com.jhmk.warn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.jhmk.cloudentity","com.jhmk.cloudservice","com.jhmk.cloudutil"})
@EnableTransactionManagement
public class WarnApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarnApplication.class, args);
	}
}
