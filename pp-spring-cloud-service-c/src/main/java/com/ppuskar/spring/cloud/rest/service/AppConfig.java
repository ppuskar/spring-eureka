package com.ppuskar.spring.cloud.rest.service;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClientHttpRequestFactory;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

	@Bean
	public RibbonClientHttpRequestFactory ribbonClientHttpRequestFactory() {
		return new RibbonClientHttpRequestFactory(new SpringClientFactory());
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(final RibbonClientHttpRequestFactory ribbonClientHttpRequestFactory) {
		return new RestTemplate(ribbonClientHttpRequestFactory);
	}

	@Bean
	public HelloController helloController() {
		return new HelloController();
	}

}
