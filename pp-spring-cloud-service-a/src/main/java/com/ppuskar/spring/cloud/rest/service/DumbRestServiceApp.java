package com.ppuskar.spring.cloud.rest.service;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.EurekaClient;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class DumbRestServiceApp implements SysInfoController {
	@Autowired
	@Lazy
	private EurekaClient eurekaClient;

	@Value("${spring.application.name}")
	private String appName;

	public static void main(String[] args) {
		SpringApplication.run(DumbRestServiceApp.class, args);
	}

	@Override
	public String getSysInfo() throws UnknownHostException {
		return String.format("Service '%s',  hosted at '%s':", eurekaClient.getApplication(appName).getName(),
				Inet4Address.getLocalHost().getHostAddress());
	}
}