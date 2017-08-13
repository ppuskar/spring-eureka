package com.ppuskar.spring.cloud.rest.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableEurekaClient
@Import(value = { AppConfig.class })
@EnableDiscoveryClient
public class ServiceCApp {

	public static void main(String[] args) {
		SpringApplication.run(ServiceCApp.class, args);
	}

}