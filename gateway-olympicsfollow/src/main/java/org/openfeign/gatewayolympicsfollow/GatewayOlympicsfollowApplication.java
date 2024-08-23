package org.openfeign.gatewayolympicsfollow;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class GatewayOlympicsfollowApplication {

  @Operation(
    summary = "Inicia o Gateway dos microserviços",
    description = "Inicia o Gateway dos microserviços, que é responsável por receber as requisições e direcioná-las para o microserviço correto."
  )
  public static void main(String[] args) {
    SpringApplication.run(GatewayOlympicsfollowApplication.class, args);
  }

}
