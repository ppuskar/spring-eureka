package com.ppuskar.spring.cloud.generic.client;

import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;

public class GenericClientB {

	private static ApplicationInfoManager applicationInfoManager;
	private static EurekaClient eurekaClient;

	private static synchronized ApplicationInfoManager initializeApplicationInfoManager(
			EurekaInstanceConfig instanceConfig) {

		if (applicationInfoManager == null) {
			InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
			applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);
		}

		return applicationInfoManager;
	}

	private static synchronized EurekaClient initializeEurekaClient(ApplicationInfoManager applicationInfoManager,
			EurekaClientConfig clientConfig) {
		if (eurekaClient == null) {
			eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
		}

		return eurekaClient;
	}

	public void sendRequestToServiceUsingEureka(EurekaClient eurekaClient) throws InterruptedException {

		String vipAddress = "SPRING-REST-SERVICE-C";

		InstanceInfo nextServerInfo = null;
		try {
			nextServerInfo = eurekaClient.getNextServerFromEureka(vipAddress, false);
		} catch (Exception e) {
			System.err.println("Cannot get an instance of example service to talk to from eureka");
			System.exit(-1);
		}

		System.out.println("Found an instance of example service to talk to from eureka: "
				+ nextServerInfo.getVIPAddress() + ":" + nextServerInfo.getPort());

		System.out.println("healthCheckUrl: " + nextServerInfo.getHealthCheckUrl());
		System.out.println("override: " + nextServerInfo.getOverriddenStatus());

		RestTemplate template = new RestTemplate();

		for (int i = 0; i < 20; i++) {
			String response = template.getForObject(
					("http://" + nextServerInfo.getHostName() + ":" + nextServerInfo.getPort() + "/sysInfo"),
					String.class, new Object());

			System.out.println("Response from service C for sys info :" + response);

			String responseStack = template.getForObject(
					("http://" + nextServerInfo.getHostName() + ":" + nextServerInfo.getPort() + "/stackInfo"),
					String.class, new Object());

			System.out.println("Response from service C for stack info :" + responseStack);
			Thread.sleep(10000);
		}

	}

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("eureka.preferSameZone", "true");
		System.setProperty("eureka.shouldUseDns", "false");
		System.setProperty("eureka.serviceUrl.default", "http://localhost:8761/eureka/");
		System.setProperty("eureka.decoderName", "JacksonJson");
		System.setProperty("eureka.name", "custom client");

		GenericClientB sampleClient = new GenericClientB();

		// create the client
		ApplicationInfoManager applicationInfoManager = initializeApplicationInfoManager(
				new MyDataCenterInstanceConfig());
		EurekaClient client = initializeEurekaClient(applicationInfoManager, new DefaultEurekaClientConfig());

		// use the client
		sampleClient.sendRequestToServiceUsingEureka(client);

		// shutdown the client
		eurekaClient.shutdown();
	}

}
