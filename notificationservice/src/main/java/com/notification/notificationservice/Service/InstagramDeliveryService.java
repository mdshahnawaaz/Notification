package com.notification.notificationservice.Service;

import org.springframework.stereotype.Service;

import com.notification.notificationservice.Dto.NotificationDto;
import com.notification.notificationservice.config.NotificationProviderProperties;

@Service
public class InstagramDeliveryService {

    private final NotificationProviderProperties properties;

    public InstagramDeliveryService(NotificationProviderProperties properties) {
        this.properties = properties;
    }

    public void send(NotificationDto dto) {
        if (!properties.getInstagram().isEnabled()) {
            throw new IllegalStateException(
                    "Instagram delivery is disabled. Enable it only after Meta app setup, permissions, and recipient opt-in are ready."
            );
        }

        throw new UnsupportedOperationException(
                "Instagram messaging needs Meta Graph API app approval and a provider-specific recipient id. Add the approved endpoint here after credentials are available."
        );
    }
}
