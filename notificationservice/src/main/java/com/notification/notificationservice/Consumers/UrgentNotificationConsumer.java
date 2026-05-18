package com.notification.notificationservice.Consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.notification.notificationservice.Dto.NotificationDto;
import com.notification.notificationservice.Dto.UrgentNotificationDto;
import com.notification.notificationservice.Service.NotificationDeliveryDispatcher;

@Component
public class UrgentNotificationConsumer {

    private final NotificationDeliveryDispatcher deliveryDispatcher;

    public UrgentNotificationConsumer(NotificationDeliveryDispatcher deliveryDispatcher) {
        this.deliveryDispatcher = deliveryDispatcher;
    }

    @RabbitListener(queues = "urgent.notification.queue")
    public void consume(UrgentNotificationDto urgentDto) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setType(urgentDto.getType());
        notificationDto.setRecipient(urgentDto.getRecipient());
        notificationDto.setMessage(urgentDto.getMessage());

        deliveryDispatcher.dispatch(notificationDto);
    }
}
