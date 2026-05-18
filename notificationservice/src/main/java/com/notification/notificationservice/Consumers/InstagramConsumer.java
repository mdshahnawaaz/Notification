package com.notification.notificationservice.Consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.notification.notificationservice.Dto.NotificationDto;
import com.notification.notificationservice.Service.InstagramDeliveryService;

@Component
public class InstagramConsumer {

    private final InstagramDeliveryService instagramDeliveryService;

    public InstagramConsumer(InstagramDeliveryService instagramDeliveryService) {
        this.instagramDeliveryService = instagramDeliveryService;
    }

    @RabbitListener(queues = "instagram.queue")
    public void consume(NotificationDto dto) {
        instagramDeliveryService.send(dto);
    }
}
