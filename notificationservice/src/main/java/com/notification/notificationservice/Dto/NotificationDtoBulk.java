package com.notification.notificationservice.Dto;

import java.util.List;

import com.notification.notificationservice.Entity.UserType;

public class NotificationDtoBulk {

    int templateId;
    List<NotificationRecipientDto> recipients;
    UserType userType;
    public NotificationDtoBulk() {
    }

    public NotificationDtoBulk(int templateId, List<NotificationRecipientDto> recipients, UserType userType) {
        this.templateId = templateId;
        this.recipients = recipients;
        this.userType = userType;
    }

    public int getTemplateId() {
        return templateId;
    }
    public List<NotificationRecipientDto> getRecipients() {
        return recipients;
    }
    public UserType getUserType() {
        return userType;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }
    public void setRecipients(List<NotificationRecipientDto> recipients) {
        this.recipients = recipients;
    }
    public void setUserType(UserType userType) {
        this.userType = userType;   
    }

}
