package com.juanfe.notifications_microservice.service;

import com.juanfe.notifications_microservice.dto.NotificationEvent;
import com.juanfe.notifications_microservice.model.Notification;
import com.juanfe.notifications_microservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @KafkaListener(topics = "level-up", groupId = "notifications", containerFactory = "notificationEventKafkaListenerContainerFactory")
    public void handleLevelUp(NotificationEvent payload) {
        Notification notification = new Notification();
        notification.setEventType(payload.getEventType());
        String details = payload.getEventDetails().orElse("");
        notification.setContent("Congratulations! You've reached level " + details + "!");
        notification.setRecipient(payload.getRecipient());
        notification.setSender(payload.getSender());
        notification.setStatus("UNREAD");
        notification.setTimestamp(System.currentTimeMillis());
        notificationRepository.save(notification);
    }

    @KafkaListener(topics = "item-acquired", groupId = "notifications", containerFactory = "notificationEventKafkaListenerContainerFactory")
    public void handleItemAcquired(NotificationEvent payload) {
        Notification notification = new Notification();
        notification.setEventType(payload.getEventType());
        String details = payload.getEventDetails().orElse("");
        notification.setContent("Awesome! You've obtained: " + details);
        notification.setRecipient(payload.getRecipient());
        notification.setSender(payload.getSender());
        notification.setStatus("UNREAD");
        notification.setTimestamp(System.currentTimeMillis());
        notificationRepository.save(notification);
    }

    @KafkaListener(topics = "challenge-completed", groupId = "notifications", containerFactory = "notificationEventKafkaListenerContainerFactory")
    public void handleChallengeCompleted(NotificationEvent payload) {
        Notification notification = new Notification();
        notification.setEventType(payload.getEventType());
        String details = payload.getEventDetails().orElse("");
        notification.setContent("Well done! Challenge completed: " + details);
        notification.setRecipient(payload.getRecipient());
        notification.setSender(payload.getSender());
        notification.setStatus("UNREAD");
        notification.setTimestamp(System.currentTimeMillis());
        notificationRepository.save(notification);
    }

    @KafkaListener(topics = "pvp-event", groupId = "notifications", containerFactory = "notificationEventKafkaListenerContainerFactory")
    public void handlePvPEvent(NotificationEvent payload) {
        Notification notification = new Notification();
        notification.setEventType(payload.getEventType());
        String details = payload.getEventDetails().orElse("");
        notification.setContent("PvP Alert: " + details);
        notification.setRecipient(payload.getRecipient());
        notification.setSender(payload.getSender());
        notification.setStatus("UNREAD");
        notification.setTimestamp(System.currentTimeMillis());
        notificationRepository.save(notification);
    }

    @KafkaListener(topics = "friend-request", groupId = "notifications", containerFactory = "notificationEventKafkaListenerContainerFactory")
    public void handleFriendRequest(NotificationEvent payload) {
        Notification notification = new Notification();
        notification.setEventType(payload.getEventType());
        notification.setContent("You have a new friend request from: " + payload.getSender());
        notification.setRecipient(payload.getRecipient());
        notification.setSender(payload.getSender());
        notification.setStatus("UNREAD");
        notification.setTimestamp(System.currentTimeMillis());
        notificationRepository.save(notification);
    }

    @KafkaListener(topics = "friend-accepted", groupId = "notifications", containerFactory = "notificationEventKafkaListenerContainerFactory")
    public void handleFriendAccepted(NotificationEvent payload) {
        Notification notification = new Notification();
        notification.setEventType(payload.getEventType());
        notification.setContent(payload.getSender() + " accepted your friend request!");
        notification.setRecipient(payload.getRecipient());
        notification.setSender(payload.getSender());
        notification.setStatus("UNREAD");
        notification.setTimestamp(System.currentTimeMillis());
        notificationRepository.save(notification);
    }

    @KafkaListener(topics = "new-follower", groupId = "notifications", containerFactory = "notificationEventKafkaListenerContainerFactory")
    public void handleNewFollower(NotificationEvent payload) {
        Notification notification = new Notification();
        notification.setEventType(payload.getEventType());
        notification.setContent("You have a new follower: " + payload.getSender());
        notification.setRecipient(payload.getRecipient());
        notification.setSender(payload.getSender());
        notification.setStatus("UNREAD");
        notification.setTimestamp(System.currentTimeMillis());
        notificationRepository.save(notification);
    }
}
