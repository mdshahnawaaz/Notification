package com.notification.notificationservice.config.rabbitMq;

import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqJsonConfig {

    @Bean
    public JacksonJsonMessageConverter converter() {
        return new JacksonJsonMessageConverter();
    }
}
