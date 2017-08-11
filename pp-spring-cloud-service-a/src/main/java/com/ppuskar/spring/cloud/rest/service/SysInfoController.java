package com.ppuskar.spring.cloud.rest.service;

import java.net.UnknownHostException;

import org.springframework.web.bind.annotation.RequestMapping;

public interface SysInfoController {
	@RequestMapping("/sysInfo")
	String getSysInfo() throws UnknownHostException;
}