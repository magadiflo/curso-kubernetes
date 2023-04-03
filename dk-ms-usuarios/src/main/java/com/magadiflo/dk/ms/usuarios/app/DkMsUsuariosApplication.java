package com.magadiflo.dk.ms.usuarios.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class DkMsUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(DkMsUsuariosApplication.class, args);
	}

}
