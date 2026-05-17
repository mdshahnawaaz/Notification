package com.notification.notificationservice.Consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.notification.notificationservice.Dto.NotificationDto;

@Component
public class EmailConsumer {

    @RabbitListener(queues = "email.queue")
    public void consume(NotificationDto dto) {

        System.out.println("EMAIL CONSUMER");

        System.out.println(dto.getRecipient());
        System.out.println(dto.getMessage());

        // SMTP Logic here
    }
}
