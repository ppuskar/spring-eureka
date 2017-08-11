package com.ppuskar.spring.cloud.generic.client;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryManager;

public class GenericClientApp {

	@SuppressWarnings("deprecation")
	public static void main(String args[]) {
		System.setProperty("eureka.preferSameZone", "true");
		System.setProperty("eureka.shouldUseDns", "false");
		System.setProperty("eureka.serviceUrl.default", "http://localhost:8761/eureka/");
		System.setProperty("eureka.decoderName", "JacksonJson");

		DiscoveryManager.getInstance().initComponent(new MyDataCenterInstanceConfig(), new DefaultEurekaClientConfig());

		String vipAddress = "SPRING-REST-SERVICE-C";
		InstanceInfo nextServerInfo = null;
		try {
			nextServerInfo = DiscoveryManager.getInstance().getEurekaClient().getNextServerFromEureka(vipAddress,
					false);
		} catch (Exception e) {
			System.err.println("Cannot get an instance of example service to talk to from eureka");
			System.exit(-1);
		}

		System.out.println("Found an instance of example service to talk to from eureka: "
				+ nextServerInfo.getVIPAddress() + ":" + nextServerInfo.getPort());

		System.out.println("healthCheckUrl: " + nextServerInfo.getHealthCheckUrl());
		System.out.println("override: " + nextServerInfo.getOverriddenStatus());

		System.out.println("Server Host Name " + nextServerInfo.getHostName() + " at port " + nextServerInfo.getPort());
	}

}
