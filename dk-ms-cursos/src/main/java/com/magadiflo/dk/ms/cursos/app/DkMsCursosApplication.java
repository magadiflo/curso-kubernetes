package com.magadiflo.dk.ms.cursos.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class DkMsCursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(DkMsCursosApplication.class, args);
	}

}
