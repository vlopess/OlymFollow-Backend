package com.email.EmailOlympicsFollow.controllers;

import com.email.EmailOlympicsFollow.dtos.EmailDTO;
import com.email.EmailOlympicsFollow.entitites.Email;
import com.email.EmailOlympicsFollow.services.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

  @Autowired
  private EmailService emailService;

  @PostMapping("/send")
  @Operation(
    summary = "Envia um email",
    description = "Recebe as informações necessárias para enviar um email e envia o email."
  )
  public ResponseEntity<Email> send(@RequestBody EmailDTO info) {
    return new ResponseEntity<Email>(emailService.sendEmail(info), HttpStatus.CREATED);
  }
}