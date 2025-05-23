package com.juanfe.notifications_microservice.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum NotificationCategory {
    GAME_EVENT(new HashSet<>(Arrays.asList(
            "level-up",
            "item-acquired",
            "challenge-completed",
            "pvp-event"
    ))),
    SOCIAL_EVENT(new HashSet<>(Arrays.asList(
            "friend-request",
            "friend-accepted",
            "new-follower"
    )));

    private final Set<String> eventTypes;

    NotificationCategory(Set<String> eventTypes) {
        this.eventTypes = eventTypes;
    }

    public static NotificationCategory fromEventType(String eventType) {
        for (NotificationCategory category : NotificationCategory.values()) {
            if (category.eventTypes.contains(eventType)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown event type: " + eventType);
    }

    public Set<String> getEventTypes() {
        return eventTypes;
    }
}