package com.notification.notificationservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notification.notificationservice.Entity.MessageTemplate;

public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Integer> {
}
