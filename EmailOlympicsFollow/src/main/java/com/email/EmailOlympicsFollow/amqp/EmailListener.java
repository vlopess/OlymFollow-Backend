package com.email.EmailOlympicsFollow.amqp;

import com.email.EmailOlympicsFollow.dtos.EmailDTO;
import com.email.EmailOlympicsFollow.entitites.Email;
import com.email.EmailOlympicsFollow.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailListener {
    private EmailService emailService;

    @Autowired
    public EmailListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "email-queue")
    public void receiveMedalha(Email emailMessage) {
        emailService.sendEmail(new EmailDTO(emailMessage));

    }
}
