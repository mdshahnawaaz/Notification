package com.notification.notificationservice.Dto;

import java.util.List;

public class BulkNotificationResponse {

    private int templateId;
    private String notificationType;
    private int requestedRecipients;
    private int publishedCount;
    private List<String> skippedRecipients;

    public BulkNotificationResponse() {
    }

    public BulkNotificationResponse(int templateId,
                                    String notificationType,
                                    int requestedRecipients,
                                    int publishedCount,
                                    List<String> skippedRecipients) {
        this.templateId = templateId;
        this.notificationType = notificationType;
        this.requestedRecipients = requestedRecipients;
        this.publishedCount = publishedCount;
        this.skippedRecipients = skippedRecipients;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public int getRequestedRecipients() {
        return requestedRecipients;
    }

    public void setRequestedRecipients(int requestedRecipients) {
        this.requestedRecipients = requestedRecipients;
    }

    public int getPublishedCount() {
        return publishedCount;
    }

    public void setPublishedCount(int publishedCount) {
        this.publishedCount = publishedCount;
    }

    public List<String> getSkippedRecipients() {
        return skippedRecipients;
    }

    public void setSkippedRecipients(List<String> skippedRecipients) {
        this.skippedRecipients = skippedRecipients;
    }
}
