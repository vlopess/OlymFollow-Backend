package com.email.EmailOlympicsFollow.amqp;

import com.email.EmailOlympicsFollow.dtos.EmailDTO;
import com.email.EmailOlympicsFollow.entitites.Email;
import com.email.EmailOlympicsFollow.services.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MedalhaListener {

  @Autowired
  private EmailService emailService;

  @RabbitListener(queues = "email-queue")
  @Operation(
    summary = "Escuta a fila email-medalha",
    description = "Escuta a fila email-medalha e envia um email para o usuário autenticado informando que um país que ele segue ganhou uma nova medalha."
  )
  public void listen(Email emailMessage) {
    emailService.sendEmail(new EmailDTO(emailMessage));
  }
}
