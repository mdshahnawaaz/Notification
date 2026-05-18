package com.notification.notificationservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "notification")
public class NotificationProviderProperties {

    private final Email email = new Email();
    private final Twilio twilio = new Twilio();
    private final Instagram instagram = new Instagram();

    public Email getEmail() {
        return email;
    }

    public Twilio getTwilio() {
        return twilio;
    }

    public Instagram getInstagram() {
        return instagram;
    }

    public static class Email {
        private String from;
        private String defaultSubject = "Notification";

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getDefaultSubject() {
            return defaultSubject;
        }

        public void setDefaultSubject(String defaultSubject) {
            this.defaultSubject = defaultSubject;
        }
    }

    public static class Twilio {
        private String accountSid;
        private String authToken;
        private String smsFrom;
        private String whatsappFrom;

        public String getAccountSid() {
            return accountSid;
        }

        public void setAccountSid(String accountSid) {
            this.accountSid = accountSid;
        }

        public String getAuthToken() {
            return authToken;
        }

        public void setAuthToken(String authToken) {
            this.authToken = authToken;
        }

        public String getSmsFrom() {
            return smsFrom;
        }

        public void setSmsFrom(String smsFrom) {
            this.smsFrom = smsFrom;
        }

        public String getWhatsappFrom() {
            return whatsappFrom;
        }

        public void setWhatsappFrom(String whatsappFrom) {
            this.whatsappFrom = whatsappFrom;
        }
    }

    public static class Instagram {
        private boolean enabled;
        private String accessToken;
        private String senderId;
        private String graphApiBaseUrl = "https://graph.facebook.com/v20.0";

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getSenderId() {
            return senderId;
        }

        public void setSenderId(String senderId) {
            this.senderId = senderId;
        }

        public String getGraphApiBaseUrl() {
            return graphApiBaseUrl;
        }

        public void setGraphApiBaseUrl(String graphApiBaseUrl) {
            this.graphApiBaseUrl = graphApiBaseUrl;
        }
    }
}
