package com.ppuskar.spring.cloud.rest.service;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.discovery.EurekaClient;

@RestController
public class HelloController {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${spring.application.name}")
	private String appName;

	@Autowired
	public DiscoveryClient discoveryClient;

	@Autowired
	@Lazy
	private EurekaClient eurekaClient;

	@Value("${spring.application.name}")
	private String serviceName;

	@RequestMapping("/stackInfo")
	public String getStackInfo() throws UnknownHostException {
		String responseA = restTemplate.getForObject("http://SPRING-REST-SERVICE-A/sysInfo", String.class,
				new Object());

		return String.format("Service '%s',  hosted at :'%s' \\n %s",
				eurekaClient.getApplication(serviceName).getName(), Inet4Address.getLocalHost().getHostAddress(),
				responseA);
	}

	@RequestMapping("/sysInfo")
	public String getInfo() throws UnknownHostException {
		return String.format("Service SPRING-REST-SERVICE-C,  hosted at :'%s'",
				Inet4Address.getLocalHost().getHostAddress());
	}
}