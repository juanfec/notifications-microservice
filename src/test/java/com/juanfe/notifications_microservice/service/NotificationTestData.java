package com.juanfe.notifications_microservice.service;

import com.juanfe.notifications_microservice.dto.NotificationEvent;
import com.juanfe.notifications_microservice.model.Notification;

import java.util.Optional;
import java.util.UUID;

public class NotificationTestData {
    private static UUID generateRandomUUID() {
        return UUID.randomUUID();
    }

    public static NotificationEvent createLevelUpEvent() {
        NotificationEvent event = new NotificationEvent();
        event.setEventType("level-up");
        event.setRecipient(generateRandomUUID());
        event.setSender(Optional.of(generateRandomUUID()));
        event.setEventDetails(Optional.of("15"));
        return event;
    }

    public static NotificationEvent createItemAcquiredEvent() {
        NotificationEvent event = new NotificationEvent();
        event.setEventType("item-acquired");
        event.setRecipient(generateRandomUUID());
        event.setSender(Optional.of(generateRandomUUID()));
        event.setEventDetails(Optional.of("Sword of Destiny"));
        return event;
    }

    public static NotificationEvent createChallengeCompletedEvent() {
        NotificationEvent event = new NotificationEvent();
        event.setEventType("challenge-completed");
        event.setRecipient(generateRandomUUID());
        event.setSender(Optional.of(generateRandomUUID()));
        event.setEventDetails(Optional.of("Dragon Slayer"));
        return event;
    }

    public static NotificationEvent createPvPEvent() {
        NotificationEvent event = new NotificationEvent();
        event.setEventType("pvp-event");
        event.setRecipient(generateRandomUUID());
        event.setSender(Optional.of(generateRandomUUID()));
        event.setEventDetails(Optional.of("You were defeated"));
        return event;
    }

    public static NotificationEvent createFriendRequestEvent() {
        NotificationEvent event = new NotificationEvent();
        event.setEventType("friend-request");
        event.setRecipient(generateRandomUUID());
        event.setSender(Optional.of(generateRandomUUID()));
        event.setEventDetails(Optional.empty());
        return event;
    }

    public static NotificationEvent createFriendAcceptedEvent() {
        NotificationEvent event = new NotificationEvent();
        event.setEventType("friend-accepted");
        event.setRecipient(generateRandomUUID());
        event.setSender(Optional.of(generateRandomUUID()));
        event.setEventDetails(Optional.empty());
        return event;
    }

    public static NotificationEvent createNewFollowerEvent() {
        NotificationEvent event = new NotificationEvent();
        event.setEventType("new-follower");
        event.setRecipient(generateRandomUUID());
        event.setSender(Optional.of(generateRandomUUID()));
        event.setEventDetails(Optional.empty());
        return event;
    }

    public static Notification createLevelUpNotification() {
        Notification notification = new Notification();
        notification.setEventType("level-up");
        notification.setContent("Congratulations! You've reached level 10!");
        notification.setRecipient(generateRandomUUID());
        notification.setSender(generateRandomUUID());
        notification.setStatus("UNREAD");
        notification.setTimestamp(System.currentTimeMillis());
        return notification;
    }

    public static Notification createItemAcquiredNotification() {
        Notification notification = new Notification();
        notification.setEventType("item-acquired");
        notification.setContent("Obtained: Sword");
        notification.setRecipient(generateRandomUUID());
        notification.setSender(generateRandomUUID());
        notification.setStatus("UNREAD");
        notification.setTimestamp(System.currentTimeMillis());
        return notification;
    }

    public static Notification createChallengeCompletedNotification() {
        Notification notification = new Notification();
        notification.setEventType("challenge-completed");
        notification.setContent("Well done! Challenge completed: Dragon Slayer");
        notification.setRecipient(generateRandomUUID());
        notification.setSender(generateRandomUUID());
        notification.setStatus("UNREAD");
        notification.setTimestamp(System.currentTimeMillis());
        return notification;
    }

    public static Notification createPvPNotification() {
        Notification notification = new Notification();
        notification.setEventType("pvp-event");
        notification.setContent("PvP Alert: You were defeated");
        notification.setRecipient(generateRandomUUID());
        notification.setSender(generateRandomUUID());
        notification.setStatus("UNREAD");
        notification.setTimestamp(System.currentTimeMillis());
        return notification;
    }

    public static Notification createFriendRequestNotification() {
        Notification notification = new Notification();
        notification.setEventType("friend-request");
        notification.setContent("You have a new friend request from: test-sender");
        notification.setRecipient(generateRandomUUID());
        notification.setSender(generateRandomUUID());
        notification.setStatus("UNREAD");
        notification.setTimestamp(System.currentTimeMillis());
        return notification;
    }

    public static Notification createFriendAcceptedNotification() {
        Notification notification = new Notification();
        notification.setEventType("friend-accepted");
        notification.setContent("test-sender accepted your friend request!");
        notification.setRecipient(generateRandomUUID());
        notification.setSender(generateRandomUUID());
        notification.setStatus("UNREAD");
        notification.setTimestamp(System.currentTimeMillis());
        return notification;
    }

    public static Notification createNewFollowerNotification() {
        Notification notification = new Notification();
        notification.setEventType("new-follower");
        notification.setContent("You have a new follower: test-sender");
        notification.setRecipient(generateRandomUUID());
        notification.setSender(generateRandomUUID());
        notification.setStatus("UNREAD");
        notification.setTimestamp(System.currentTimeMillis());
        return notification;
    }
}
