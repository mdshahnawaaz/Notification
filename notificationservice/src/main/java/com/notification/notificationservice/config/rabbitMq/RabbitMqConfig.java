package com.notification.notificationservice.config.rabbitMq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;


@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue emailQueue() {
        return new Queue("email.queue");
    }

    @Bean
    public Queue whatsappQueue() {
        return new Queue("whatsapp.queue");
    }

    @Bean
    public Queue instagramQueue() {
        return new Queue("instagram.queue");
    }
    
    @Bean
    public Queue smsQueue() {
        return new Queue("sms.queue");      
    }

    @Bean
    public Queue urgentNotificationQueue() {
        return QueueBuilder.durable("urgent.notification.queue")
                .maxPriority(10)
                .build();
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("notification.exchange");
    }


    @Bean
    public Binding bindingEmailQueue() {
        return BindingBuilder.bind(emailQueue()).to(directExchange()).with("email.routing.key");
    }
    
    @Bean
    public Binding bindingWhatsappQueue() {
        return BindingBuilder.bind(whatsappQueue()).to(directExchange()).with("whatsapp.routing.key");
    }

    @Bean       
    public Binding bindingInstagramQueue() {
        return BindingBuilder.bind(instagramQueue()).to(directExchange()).with("instagram.routing.key");
    }

    @Bean
    public Binding bindingSmsQueue() {
        return BindingBuilder.bind(smsQueue()).to(directExchange()).with("sms.routing.key");
    }

    @Bean
    public Binding bindingUrgentNotificationQueue() {
        return BindingBuilder.bind(urgentNotificationQueue()).to(directExchange()).with("urgent.notification.routing.key");
    }
    
}
