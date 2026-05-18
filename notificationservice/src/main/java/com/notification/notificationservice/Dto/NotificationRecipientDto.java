package com.notification.notificationservice.Dto;

import java.util.Map;

import com.notification.notificationservice.Entity.UserType;

public class NotificationRecipientDto {

    private Integer id;
    private String name;
    private String email;
    private String recipient;
    private UserType userType;
    private Map<String, String> attributes;

    public NotificationRecipientDto() {
    }

    public NotificationRecipientDto(Integer id,
                                    String name,
                                    String email,
                                    String recipient,
                                    UserType userType,
                                    Map<String, String> attributes) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.recipient = recipient;
        this.userType = userType;
        this.attributes = attributes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
