package com.magadiflo.dk.ms.gateway.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DkMsGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(DkMsGatewayApplication.class, args);
	}

}
