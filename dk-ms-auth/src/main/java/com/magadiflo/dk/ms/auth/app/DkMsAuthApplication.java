package com.magadiflo.dk.ms.auth.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DkMsAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(DkMsAuthApplication.class, args);
	}

}
