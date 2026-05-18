package com.notification.notificationservice.Service;

import org.springframework.stereotype.Service;

import com.notification.notificationservice.Dto.NotificationDto;

@Service
public class NotificationDeliveryDispatcher {

    private final EmailDeliveryService emailDeliveryService;
    private final TwilioDeliveryService twilioDeliveryService;
    private final InstagramDeliveryService instagramDeliveryService;

    public NotificationDeliveryDispatcher(EmailDeliveryService emailDeliveryService,
                                          TwilioDeliveryService twilioDeliveryService,
                                          InstagramDeliveryService instagramDeliveryService) {
        this.emailDeliveryService = emailDeliveryService;
        this.twilioDeliveryService = twilioDeliveryService;
        this.instagramDeliveryService = instagramDeliveryService;
    }

    public void dispatch(NotificationDto dto) {
        if (dto == null || dto.getType() == null) {
            throw new IllegalArgumentException("Notification type is required.");
        }

        switch (dto.getType().toLowerCase()) {
            case "email":
                emailDeliveryService.send(dto);
                break;
            case "sms":
                twilioDeliveryService.sendSms(dto.getRecipient(), dto.getMessage());
                break;
            case "whatsapp":
                twilioDeliveryService.sendWhatsapp(dto.getRecipient(), dto.getMessage());
                break;
            case "instagram":
                instagramDeliveryService.send(dto);
                break;
            default:
                throw new IllegalArgumentException("Unsupported notification type: " + dto.getType());
        }
    }
}
