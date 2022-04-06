package com.example.SpringbootProject;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
//@ConfigurationProperties("spring.datasource")
public class DataConfiguration {

	@Profile("dev")
	@Bean
	public String devDataConfiguration() {
		System.out.println("Data connection for DEV - Jira");
		return "Data connection for Dev"; 
	}
	@Profile("prod")
	@Bean
	public String prodDataConfiguration() {
		System.out.println("Data connection for PROD - Low Cost Instance");
		return "Data connection for Prod";
	}
	@Profile("test")
	@Bean
	public String testDataConfiguration() {
		System.out.println("Data connection for TEST - High Performance Instance");
		return "Data connection for Test";
	}
}