package com.notification.notificationservice.Consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.notification.notificationservice.Dto.NotificationDto;
import com.notification.notificationservice.Service.EmailDeliveryService;

@Component
public class EmailConsumer {

    private final EmailDeliveryService emailDeliveryService;

    public EmailConsumer(EmailDeliveryService emailDeliveryService) {
        this.emailDeliveryService = emailDeliveryService;
    }

    @RabbitListener(queues = "email.queue")
    public void consume(NotificationDto dto) {
        emailDeliveryService.send(dto);
    }
}
