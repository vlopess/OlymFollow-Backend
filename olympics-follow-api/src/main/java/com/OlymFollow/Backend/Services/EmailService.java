package com.OlymFollow.Backend.Services;

import com.email.EmailOlympicsFollow.entitites.Email;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public EmailService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEmail(Email emailMessage) {
        Message message = new Message(emailMessage.toString().getBytes());
        rabbitTemplate.convertAndSend("email-queue", emailMessage);
    }
}
