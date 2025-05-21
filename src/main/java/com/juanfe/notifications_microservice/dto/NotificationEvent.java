package com.juanfe.notifications_microservice.dto;

import lombok.Data;

import java.util.Optional;

@Data
public class NotificationEvent {
    private String eventType;
    private String recipient;
    private String sender;
    private Optional<String> eventDetails = Optional.empty(); // generic details for any event
}
