package com.notification.notificationservice.config.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.notification.notificationservice.Entity.Role;
import com.notification.notificationservice.Entity.User;

@Service
public class JwtService {

    private static final String HMAC_ALGORITHM = "HmacSHA256";

    private final byte[] secret;
    private final long expirationSeconds;

    public JwtService(@Value("${notification.jwt.secret}") String secret,
                      @Value("${notification.jwt.expiration-seconds:86400}") long expirationSeconds) {
        if (secret == null || secret.length() < 32) {
            throw new IllegalStateException("notification.jwt.secret must be at least 32 characters.");
        }

        this.secret = secret.getBytes(StandardCharsets.UTF_8);
        this.expirationSeconds = expirationSeconds;
    }

    public String generateToken(User user) {
        Instant now = Instant.now();
        long issuedAt = now.getEpochSecond();
        long expiresAt = now.plusSeconds(expirationSeconds).getEpochSecond();

        String headerJson = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        String payloadJson = "{"
                + "\"sub\":\"" + escapeJson(user.getEmail()) + "\","
                + "\"userId\":" + user.getId() + ","
                + "\"name\":\"" + escapeJson(user.getName()) + "\","
                + "\"role\":\"" + user.getRole().name() + "\","
                + "\"userType\":\"" + user.getUserType().name() + "\","
                + "\"iat\":" + issuedAt + ","
                + "\"exp\":" + expiresAt
                + "}";

        String unsignedToken = base64Url(headerJson) + "." + base64Url(payloadJson);
        return unsignedToken + "." + sign(unsignedToken);
    }

    public Optional<JwtUserPrincipal> parseToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return Optional.empty();
            }

            String unsignedToken = parts[0] + "." + parts[1];
            if (!constantTimeEquals(sign(unsignedToken), parts[2])) {
                return Optional.empty();
            }

            String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);

            Long expiresAt = extractLongClaim(payloadJson, "exp");
            if (expiresAt == null || Instant.now().getEpochSecond() >= expiresAt) {
                return Optional.empty();
            }

            String email = extractStringClaim(payloadJson, "sub");
            String role = extractStringClaim(payloadJson, "role");
            if (email == null || role == null) {
                return Optional.empty();
            }

            return Optional.of(new JwtUserPrincipal(email, Role.valueOf(role)));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    private String base64Url(String value) {
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private String sign(String value) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(new SecretKeySpec(secret, HMAC_ALGORITHM));
            return Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(mac.doFinal(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception ex) {
            throw new IllegalStateException("Could not sign JWT.", ex);
        }
    }

    private boolean constantTimeEquals(String first, String second) {
        byte[] firstBytes = first.getBytes(StandardCharsets.UTF_8);
        byte[] secondBytes = second.getBytes(StandardCharsets.UTF_8);

        if (firstBytes.length != secondBytes.length) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < firstBytes.length; i++) {
            result |= firstBytes[i] ^ secondBytes[i];
        }

        return result == 0;
    }

    private String escapeJson(String value) {
        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private String extractStringClaim(String json, String key) {
        Matcher matcher = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*\"([^\"]*)\"").matcher(json);
        if (!matcher.find()) {
            return null;
        }

        return matcher.group(1);
    }

    private Long extractLongClaim(String json, String key) {
        Matcher matcher = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*(\\d+)").matcher(json);
        if (!matcher.find()) {
            return null;
        }

        return Long.parseLong(matcher.group(1));
    }
}
