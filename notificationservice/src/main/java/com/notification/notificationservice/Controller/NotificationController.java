package com.notification.notificationservice.Controller;

import org.springframework.web.bind.annotation.*;

import com.notification.notificationservice.Dto.BulkNotificationResponse;
import com.notification.notificationservice.Dto.NotificationDto;
import com.notification.notificationservice.Dto.NotificationDtoBulk;
import com.notification.notificationservice.Dto.UrgentNotificationDto;
import com.notification.notificationservice.Dto.UrgentNotificationResponse;
import com.notification.notificationservice.Service.NotificationBulkService;
import com.notification.notificationservice.Service.NotificationProducer;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationProducer producer;
    private final NotificationBulkService notificationBulkService;

    public NotificationController(NotificationProducer producer,
                                  NotificationBulkService notificationBulkService) {
        this.producer = producer;
        this.notificationBulkService = notificationBulkService;
    }

    @PostMapping
    public String send(@RequestBody NotificationDto dto) {

        producer.send(dto);

        return "Notification Published";
    }

    @PostMapping("/bulk")
    public BulkNotificationResponse sendBulk(@RequestBody NotificationDtoBulk dto) {
        return notificationBulkService.sendBulkNotifications(dto);
    }

    @PostMapping("/urgent")
    public UrgentNotificationResponse sendUrgent(@RequestBody UrgentNotificationDto dto) {
        return producer.sendUrgent(dto);
    }
}
