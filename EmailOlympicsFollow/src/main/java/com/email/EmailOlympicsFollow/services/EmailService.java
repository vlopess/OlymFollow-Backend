package com.email.EmailOlympicsFollow.services;

import com.email.EmailOlympicsFollow.dtos.EmailDTO;
import com.email.EmailOlympicsFollow.entitites.Email;
import com.email.EmailOlympicsFollow.models.EmailStatus;
import com.email.EmailOlympicsFollow.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

  @Autowired
  private EmailRepository emailRepository;

  @Autowired
  private JavaMailSender emailSender;

  @Value("${spring.mail.username}")
  private String from;

  public Email sendEmail(EmailDTO dto) {
    Email data = new Email(dto);
    data.setSendDateEmail(LocalDateTime.now());
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(from);
    message.setTo(dto.mailTo());
    message.setSubject(dto.mailSubject());
    message.setText(dto.mailText());
    data.setStatus(EmailStatus.SENT);
    emailSender.send(message);
    emailRepository.save(data);
    return data;
  }
}
