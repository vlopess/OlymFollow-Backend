package com.OlymFollow.Backend.Clients;

import com.email.EmailOlympicsFollow.dtos.EmailDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("email")
public interface EmailClient {

  @PostMapping("/email/send")
  @Operation(
    summary = "Método de envio de email",
    description = "Realiza a chamada para que o serviço de email envie um email com as informações da medalha cadastrada."
  )
  public ResponseEntity<EmailDTO> sendEmail(@RequestBody EmailDTO emailDTO);
}
