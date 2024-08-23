package com.eureka.eureka_server;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

	@Operation(
		summary = "Inicia o servidor Eureka",
		description = "Inicia o servidor Eureka, que é responsável por registrar os microserviços e permitir que eles se comuniquem entre si."
	)
	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}

}
