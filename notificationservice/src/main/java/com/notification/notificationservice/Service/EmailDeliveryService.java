package com.notification.notificationservice.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.notification.notificationservice.Dto.NotificationDto;
import com.notification.notificationservice.config.NotificationProviderProperties;

@Service
public class EmailDeliveryService {

    private final JavaMailSender mailSender;
    private final NotificationProviderProperties properties;

    public EmailDeliveryService(JavaMailSender mailSender,
                                NotificationProviderProperties properties) {
        this.mailSender = mailSender;
        this.properties = properties;
    }

    public void send(NotificationDto dto) {
        requireText(properties.getEmail().getFrom(), "notification.email.from");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(properties.getEmail().getFrom());
        message.setTo(dto.getRecipient());
        message.setSubject(properties.getEmail().getDefaultSubject());
        message.setText(dto.getMessage());

        mailSender.send(message);
    }

    private void requireText(String value, String propertyName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException(propertyName + " is required to send email notifications.");
        }
    }
}
