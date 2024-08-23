package com.email.EmailOlympicsFollow.amqp;

import com.email.EmailOlympicsFollow.dtos.EmailDTO;
import com.email.EmailOlympicsFollow.entitites.Email;
import com.email.EmailOlympicsFollow.services.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MedalhaListener {

  @Autowired
  private EmailService emailService;

  @RabbitListener(queues = "email-medalha")
  @Operation(
    summary = "Escuta a fila email-medalha",
    description = "Escuta a fila email-medalha e envia um email para o usuário autenticado informando que um país que ele segue ganhou uma nova medalha."
  )
  public void listen(@Payload Map<String, Object> medalhaData) {
    String nomeAtleta = (String) medalhaData.get("nomeAtleta");
    String pais = (String) medalhaData.get("country");
    String tipoMedalha = (String) medalhaData.get("medalha");
    String esporte = (String) medalhaData.get("esporte");

    Email email = new Email();
    email.setMailFrom("segiocerq11@gmail.com");
    email.setMailTo("segiocerq11@gmail.com");
    email.setMailSubject("Um país que você segue ganhou uma nova medalha!");
    email.setMailText("OlympicsFollow informa: " + nomeAtleta + " do " + pais +
      " ganhou uma medalha de " + tipoMedalha + " no(a) " + esporte + ".");

    // Obter o email do usuário autenticado
    // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    // User user = (User) authentication.getPrincipal();
    // String emailUsuario = user.getEmail();

    // System.out.println(emailUsuario);

    emailService.sendEmail(new EmailDTO(email));
  }
}
