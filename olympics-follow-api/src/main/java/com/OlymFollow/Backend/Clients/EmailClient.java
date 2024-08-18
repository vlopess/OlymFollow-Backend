package com.OlymFollow.Backend.Clients;

import com.email.EmailOlympicsFollow.dtos.EmailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("email")
public interface EmailClient {

  @PostMapping("/email/send")
  public ResponseEntity<EmailDTO> sendEmail(@RequestBody EmailDTO emailDTO);
}
