package com.notification.notificationservice.Dto;

import java.time.Instant;

public class UrgentNotificationResponse {

    private String status;
    private String eventType;
    private String referenceId;
    private String type;
    private String recipient;
    private Integer priority;
    private Instant queuedAt;

    public UrgentNotificationResponse() {
    }

    public UrgentNotificationResponse(String status,
                                      String eventType,
                                      String referenceId,
                                      String type,
                                      String recipient,
                                      Integer priority,
                                      Instant queuedAt) {
        this.status = status;
        this.eventType = eventType;
        this.referenceId = referenceId;
        this.type = type;
        this.recipient = recipient;
        this.priority = priority;
        this.queuedAt = queuedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Instant getQueuedAt() {
        return queuedAt;
    }

    public void setQueuedAt(Instant queuedAt) {
        this.queuedAt = queuedAt;
    }
}
