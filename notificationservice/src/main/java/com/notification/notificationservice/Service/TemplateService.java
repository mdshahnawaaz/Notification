package com.notification.notificationservice.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    private static final Pattern PATTERN = Pattern.compile("\\{\\{?\\s*([A-Za-z0-9_.]+)\\s*}?\\}");

    public String parseTemplate(String template, Map<String, String> values) {

        Matcher matcher = PATTERN.matcher(template);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {

            String key = matcher.group(1);

            String replacement = values.get(key);

            // if value is null remove placeholder
            if (replacement == null || replacement.isBlank()) {
                replacement = "";
            }

            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }

        matcher.appendTail(result);

        // remove extra spaces
        return result.toString().replaceAll("\\s+", " ").trim();
    }
}
