package com.email.EmailOlympicsFollow.entitites;

import com.email.EmailOlympicsFollow.dtos.EmailDTO;
import com.email.EmailOlympicsFollow.models.EmailStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "emails")
public class Email {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String mailFrom;
  private String mailTo;
  private String mailSubject;
  private String mailText;
  private LocalDateTime sendDateEmail;
  @Enumerated(EnumType.STRING)
  private EmailStatus status = EmailStatus.SENT;

  public Email(EmailDTO emailDTO) {
    this.mailFrom = emailDTO.mailFrom();
    this.mailTo = emailDTO.mailTo();
    this.mailSubject = emailDTO.mailSubject();
    this.mailText = emailDTO.mailText();
  }

  public Email() {}

  public String getMailTo() {
    return mailTo;
  }

  public Long getId() {
    return id;
  }

  public String getMailFrom() {
    return mailFrom;
  }

  public String getMailSubject() {
    return mailSubject;
  }

  public String getMailText() {
    return mailText;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setSendDateEmail(LocalDateTime sendDateEmail) {
    this.sendDateEmail = sendDateEmail;
  }

  public void setStatus(EmailStatus status) {
    this.status = status;
  }

  public void setMailFrom(String mailFrom) {
    this.mailFrom = mailFrom;
  }

  public void setMailTo(String mailTo) {
    this.mailTo = mailTo;
  }

  public void setMailSubject(String mailSubject) {
    this.mailSubject = mailSubject;
  }

  public void setMailText(String mailText) {
    this.mailText = mailText;
  }
}
