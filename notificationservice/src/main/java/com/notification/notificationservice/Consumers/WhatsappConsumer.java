package com.notification.notificationservice.Consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.notification.notificationservice.Dto.NotificationDto;
import com.notification.notificationservice.Service.TwilioDeliveryService;

@Component
public class WhatsappConsumer {

    private final TwilioDeliveryService twilioDeliveryService;

    public WhatsappConsumer(TwilioDeliveryService twilioDeliveryService) {
        this.twilioDeliveryService = twilioDeliveryService;
    }

    @RabbitListener(queues = "whatsapp.queue")
    public void consume(NotificationDto dto) {
        twilioDeliveryService.sendWhatsapp(dto.getRecipient(), dto.getMessage());
    }
}
