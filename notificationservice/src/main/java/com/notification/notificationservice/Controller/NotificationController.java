package com.notification.notificationservice.Controller;

import org.springframework.web.bind.annotation.*;

import com.notification.notificationservice.Dto.NotificationDto;
import com.notification.notificationservice.Service.NotificationProducer;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationProducer producer;

    public NotificationController(NotificationProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public String send(@RequestBody NotificationDto dto) {

        producer.send(dto);

        return "Notification Published";
    }
}
