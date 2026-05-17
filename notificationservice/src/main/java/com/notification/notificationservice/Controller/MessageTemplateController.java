package com.notification.notificationservice.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notification.notificationservice.Entity.MessageTemplate;
import com.notification.notificationservice.Repository.MessageTemplateRepository;

@RestController
@RequestMapping("/templates")
public class MessageTemplateController {

    private final MessageTemplateRepository messageTemplateRepository;

    public MessageTemplateController(MessageTemplateRepository messageTemplateRepository) {
        this.messageTemplateRepository = messageTemplateRepository;
    }

    @GetMapping
    public List<MessageTemplate> getTemplates() {
        return messageTemplateRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createTemplate(@RequestBody MessageTemplate request) {
        if (isBlank(request.getType()) || isBlank(request.getTemplate())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Type and template are required."));
        }

        MessageTemplate template = new MessageTemplate();
        template.setType(request.getType().trim().toUpperCase());
        template.setTemplate(request.getTemplate().trim());

        MessageTemplate savedTemplate = messageTemplateRepository.save(template);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTemplate);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
