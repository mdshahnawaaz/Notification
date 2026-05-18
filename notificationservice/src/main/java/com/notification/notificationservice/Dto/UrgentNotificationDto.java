package com.notification.notificationservice.Dto;

import java.time.Instant;
import java.util.Map;

public class UrgentNotificationDto {

    private String recipient;
    private String message;
    private String type;
    private String eventType;
    private String referenceId;
    private Integer priority;
    private Instant createdAt;
    private Map<String, String> metadata;

    public UrgentNotificationDto() {
    }

    public UrgentNotificationDto(String recipient,
                                 String message,
                                 String type,
                                 String eventType,
                                 String referenceId,
                                 Integer priority,
                                 Instant createdAt,
                                 Map<String, String> metadata) {
        this.recipient = recipient;
        this.message = message;
        this.type = type;
        this.eventType = eventType;
        this.referenceId = referenceId;
        this.priority = priority;
        this.createdAt = createdAt;
        this.metadata = metadata;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
}
