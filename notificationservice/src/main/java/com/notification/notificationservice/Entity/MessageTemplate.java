package com.notification.notificationservice.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "message_templates")
public class MessageTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String type;
    private String template;

    public MessageTemplate() {
    }

    public MessageTemplate(int id, String type, String template) {
        this.id = id;
        this.type = type;
        this.template = template;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTemplate() {
        return template;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
