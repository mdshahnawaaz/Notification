package com.notification.notificationservice.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.notification.notificationservice.Dto.BulkNotificationResponse;
import com.notification.notificationservice.Dto.NotificationDto;
import com.notification.notificationservice.Dto.NotificationDtoBulk;
import com.notification.notificationservice.Dto.NotificationRecipientDto;
import com.notification.notificationservice.Entity.MessageTemplate;
import com.notification.notificationservice.Entity.User;
import com.notification.notificationservice.Repository.MessageTemplateRepository;

@Service
public class NotificationBulkService {

    private final UserService userService;
    private final NotificationProducer notificationProducer;
    private final TemplateService templateService;
    private final MessageTemplateRepository messageTemplateRepository;

    public NotificationBulkService(UserService userService,
                                   NotificationProducer notificationProducer,
                                   TemplateService templateService,
                                   MessageTemplateRepository messageTemplateRepository) {
        this.userService = userService;
        this.notificationProducer = notificationProducer;
        this.templateService = templateService;
        this.messageTemplateRepository = messageTemplateRepository;
    }

    public List<NotificationRecipientDto> getRecipientsForNotification(NotificationDtoBulk notificationDtoBulk) {
        if (notificationDtoBulk == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is required.");
        }

        boolean hasUserType = notificationDtoBulk.getUserType() != null;
        boolean hasRecipients = notificationDtoBulk.getRecipients() != null
                && !notificationDtoBulk.getRecipients().isEmpty();

        if (hasUserType == hasRecipients) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Provide exactly one recipient source: userType or recipients."
            );
        }

        if (hasUserType) {
            return userService.getUsersByType(notificationDtoBulk.getUserType())
                    .stream()
                    .map(this::fromUser)
                    .toList();
        }

        return notificationDtoBulk.getRecipients();
    }

    public BulkNotificationResponse sendBulkNotifications(NotificationDtoBulk notificationDtoBulk) {
        validateRequest(notificationDtoBulk);

        MessageTemplate template = messageTemplateRepository.findById(notificationDtoBulk.getTemplateId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Template not found."));

        List<NotificationRecipientDto> recipients = getRecipientsForNotification(notificationDtoBulk);

        if (recipients.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No recipients found for notification.");
        }

        int publishedCount = 0;
        List<String> skippedRecipients = new ArrayList<>();

        for (NotificationRecipientDto recipient : recipients) {
            String deliveryAddress = resolveDeliveryAddress(recipient);

            if (isBlank(deliveryAddress)) {
                skippedRecipients.add(resolveRecipientReference(recipient));
                continue;
            }

            String message = templateService.parseTemplate(template.getTemplate(), buildTemplateValues(recipient));
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setType(template.getType());
            notificationDto.setRecipient(deliveryAddress);
            notificationDto.setMessage(message);

            notificationProducer.send(notificationDto);
            publishedCount++;
        }

        return new BulkNotificationResponse(
                template.getId(),
                template.getType(),
                recipients.size(),
                publishedCount,
                skippedRecipients
        );
    }

    private void validateRequest(NotificationDtoBulk notificationDtoBulk) {
        if (notificationDtoBulk == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is required.");
        }

        if (notificationDtoBulk.getTemplateId() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A valid templateId is required.");
        }
    }

    private NotificationRecipientDto fromUser(User user) {
        return new NotificationRecipientDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getEmail(),
                user.getUserType(),
                Map.of()
        );
    }

    private Map<String, String> buildTemplateValues(NotificationRecipientDto recipient) {
        Map<String, String> values = new LinkedHashMap<>();
        values.put("id", Objects.toString(recipient.getId(), ""));
        values.put("name", nullToEmpty(recipient.getName()));
        values.put("email", nullToEmpty(recipient.getEmail()));
        values.put("recipient", nullToEmpty(resolveDeliveryAddress(recipient)));
        values.put("userType", Objects.toString(recipient.getUserType(), ""));
        values.put("usertype", Objects.toString(recipient.getUserType(), ""));

        if (recipient.getAttributes() != null) {
            values.putAll(recipient.getAttributes());
        }

        return values;
    }

    private String resolveDeliveryAddress(NotificationRecipientDto recipient) {
        if (recipient == null) {
            return null;
        }

        if (!isBlank(recipient.getRecipient())) {
            return recipient.getRecipient().trim();
        }

        return recipient.getEmail();
    }

    private String resolveRecipientReference(NotificationRecipientDto recipient) {
        if (recipient == null) {
            return "null-recipient";
        }

        if (recipient.getId() != null) {
            return "recipient-id-" + recipient.getId();
        }

        if (!isBlank(recipient.getEmail())) {
            return recipient.getEmail();
        }

        if (!isBlank(recipient.getName())) {
            return recipient.getName();
        }

        return "unknown-recipient";
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

}
