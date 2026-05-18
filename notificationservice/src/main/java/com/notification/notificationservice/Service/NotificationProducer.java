package com.notification.notificationservice.Service;


import java.time.Instant;

import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.notification.notificationservice.Dto.NotificationDto;
import com.notification.notificationservice.Dto.UrgentNotificationDto;
import com.notification.notificationservice.Dto.UrgentNotificationResponse;

@Service
public class NotificationProducer {

    private static final String EXCHANGE = "notification.exchange";
    private static final String URGENT_ROUTING_KEY = "urgent.notification.routing.key";

    private final RabbitTemplate rabbitTemplate;

    public NotificationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(NotificationDto dto) {
        validateNotification(dto);

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
                EXCHANGE,
                routingKey,
                dto
        );

        System.out.println("Message Published");
    }

    public UrgentNotificationResponse sendUrgent(UrgentNotificationDto dto) {
        validateUrgentNotification(dto);

        Instant queuedAt = Instant.now();
        dto.setCreatedAt(queuedAt);
        dto.setPriority(resolvePriority(dto.getPriority()));

        MessagePostProcessor priorityPostProcessor = message -> {
            message.getMessageProperties().setPriority(dto.getPriority());
            message.getMessageProperties().setHeader("eventType", dto.getEventType());
            message.getMessageProperties().setHeader("referenceId", dto.getReferenceId());
            return message;
        };

        rabbitTemplate.convertAndSend(EXCHANGE, URGENT_ROUTING_KEY, dto, priorityPostProcessor);

        return new UrgentNotificationResponse(
                "QUEUED",
                dto.getEventType(),
                dto.getReferenceId(),
                dto.getType(),
                dto.getRecipient(),
                dto.getPriority(),
                queuedAt
        );
    }

    private void validateNotification(NotificationDto dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is required.");
        }

        requireText(dto.getType(), "type");
        requireText(dto.getRecipient(), "recipient");
        requireText(dto.getMessage(), "message");
    }

    private void validateUrgentNotification(UrgentNotificationDto dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is required.");
        }

        requireText(dto.getType(), "type");
        requireText(dto.getRecipient(), "recipient");
        requireText(dto.getMessage(), "message");
        requireText(dto.getEventType(), "eventType");
    }

    private int resolvePriority(Integer priority) {
        if (priority == null) {
            return 10;
        }

        if (priority < 1 || priority > 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "priority must be between 1 and 10.");
        }

        return priority;
    }

    private void requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fieldName + " is required.");
        }
    }
}
