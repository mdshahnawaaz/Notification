package com.notification.notificationservice.config.security;

import com.notification.notificationservice.Entity.Role;

public record JwtUserPrincipal(String email, Role role) {
}
