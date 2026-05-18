package com.notification.notificationservice.Service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import com.notification.notificationservice.config.NotificationProviderProperties;

@Service
public class TwilioDeliveryService {

    private static final String TWILIO_BASE_URL = "https://api.twilio.com";

    private final RestClient restClient;
    private final NotificationProviderProperties properties;

    public TwilioDeliveryService(RestClient.Builder restClientBuilder,
                                 NotificationProviderProperties properties) {
        this.restClient = restClientBuilder.baseUrl(TWILIO_BASE_URL).build();
        this.properties = properties;
    }

    public void sendSms(String to, String message) {
        sendMessage(properties.getTwilio().getSmsFrom(), to, message);
    }

    public void sendWhatsapp(String to, String message) {
        sendMessage(
                toTwilioWhatsappAddress(properties.getTwilio().getWhatsappFrom()),
                toTwilioWhatsappAddress(to),
                message
        );
    }

    private void sendMessage(String from, String to, String message) {
        requireText(properties.getTwilio().getAccountSid(), "notification.twilio.account-sid");
        requireText(properties.getTwilio().getAuthToken(), "notification.twilio.auth-token");
        requireText(from, "Twilio from number");
        requireText(to, "recipient");

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("From", from);
        form.add("To", to);
        form.add("Body", message);

        restClient.post()
                .uri("/2010-04-01/Accounts/{accountSid}/Messages.json", properties.getTwilio().getAccountSid())
                .headers(headers -> headers.setBasicAuth(
                        properties.getTwilio().getAccountSid(),
                        properties.getTwilio().getAuthToken()
                ))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .retrieve()
                .toBodilessEntity();
    }

    private String toTwilioWhatsappAddress(String value) {
        if (value == null || value.trim().isEmpty() || value.startsWith("whatsapp:")) {
            return value;
        }

        return "whatsapp:" + value.trim();
    }

    private void requireText(String value, String propertyName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException(propertyName + " is required to send Twilio notifications.");
        }
    }
}
