package com.juanfe.notifications_microservice.dto;

import lombok.Data;

import java.util.Optional;
import java.util.UUID;

@Data
public class NotificationEvent {
    private String eventType;
    private UUID recipient;
    private Optional<UUID> sender;
    private Optional<String> eventDetails = Optional.empty(); // generic details for any event
}
