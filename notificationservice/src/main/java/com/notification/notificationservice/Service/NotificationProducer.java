package com.notification.notificationservice.Service;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.notification.notificationservice.Dto.NotificationDto;

@Service
public class NotificationProducer {

    private final RabbitTemplate rabbitTemplate;

    public NotificationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(NotificationDto dto) {

        String routingKey = "";

        switch (dto.getType().toLowerCase()) {

            case "email":
                routingKey = "email.routing.key";
                break;

            case "whatsapp":
                routingKey = "whatsapp.routing.key";
                break;

            case "instagram":
                routingKey = "instagram.routing.key";
                break;
            case "sms":
                routingKey = "sms.routing.key";
                break;

            default:
                throw new RuntimeException("Invalid type");
        }

        rabbitTemplate.convertAndSend(
                "notification.exchange",
                routingKey,
                dto
        );

        System.out.println("Message Published");
    }
}
