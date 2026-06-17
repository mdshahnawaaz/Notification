package com.notification.notificationservice.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.notification.notificationservice.Entity.Role;
import com.notification.notificationservice.Entity.User;
import com.notification.notificationservice.Entity.UserType;
import com.notification.notificationservice.Repository.UserRepository;

@Component
public class AdminUserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminEmail;
    private final String adminPassword;

    public AdminUserSeeder(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           @Value("${notification.admin.email:admin@notification.local}") String adminEmail,
                           @Value("${notification.admin.password:admin123}") String adminPassword) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminEmail = adminEmail.trim().toLowerCase();
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(String... args) {
        userRepository.findByEmail(adminEmail).ifPresentOrElse(user -> {
            boolean changed = false;

            if (user.getRole() == null) {
                user.setRole(Role.ADMIN);
                changed = true;
            }

            if (!isBcryptHash(user.getPassword())) {
                user.setPassword(passwordEncoder.encode(adminPassword));
                changed = true;
            }

            if (changed) {
                userRepository.save(user);
            }
        }, () -> {
            User admin = new User();
            admin.setName("Admin Console");
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setUserType(UserType.SUBSCRIBED);
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        });
    }

    private boolean isBcryptHash(String value) {
        return value != null && (value.startsWith("$2a$") || value.startsWith("$2b$") || value.startsWith("$2y$"));
    }
}
