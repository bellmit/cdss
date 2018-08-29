package com.jhmk.cloudpage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ComponentScan(basePackages = {"com.jhmk.cloudutil", "com.jhmk.cloudpage", "com.jhmk.cloudentity","com.jhmk.cloudservice"})
@EnableScheduling
@EnableDiscoveryClient
public class CloudPageApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudPageApplication.class, args);
    }
}
