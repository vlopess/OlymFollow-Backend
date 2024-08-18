package com.email.EmailOlympicsFollow.dtos;

import com.email.EmailOlympicsFollow.entitites.Email;

public record EmailDTO(String mailFrom, String mailTo, String mailSubject, String mailText) {
  public EmailDTO(Email email) {
    this(email.getMailFrom(), email.getMailTo(), email.getMailSubject(), email.getMailText());
  }
}
