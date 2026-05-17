package com.notification.notificationservice.Dto;

import com.notification.notificationservice.Entity.User;
import com.notification.notificationservice.Entity.UserType;

public class UserResponse {

    private int id;
    private String name;
    private String email;
    private UserType userType;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.userType = user.getUserType();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserType getUserType() {
        return userType;
    }
}
