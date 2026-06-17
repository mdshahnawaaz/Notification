package com.notification.notificationservice.Dto;

import com.notification.notificationservice.Entity.User;
import com.notification.notificationservice.Entity.UserType;
import com.notification.notificationservice.Entity.Role;

public class UserResponse {

    private int id;
    private String name;
    private String email;
    private UserType userType;
    private Role role;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.userType = user.getUserType();
        this.role = user.getRole();
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

    public Role getRole() {
        return role;
    }
}
