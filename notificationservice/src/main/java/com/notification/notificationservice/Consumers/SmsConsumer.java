package com.notification.notificationservice.Consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.notification.notificationservice.Dto.NotificationDto;
import com.notification.notificationservice.Service.TwilioDeliveryService;

@Component
public class SmsConsumer {

    private final TwilioDeliveryService twilioDeliveryService;

    public SmsConsumer(TwilioDeliveryService twilioDeliveryService) {
        this.twilioDeliveryService = twilioDeliveryService;
    }

    @RabbitListener(queues = "sms.queue")
    public void consume(NotificationDto dto) {
        twilioDeliveryService.sendSms(dto.getRecipient(), dto.getMessage());
    }
}
