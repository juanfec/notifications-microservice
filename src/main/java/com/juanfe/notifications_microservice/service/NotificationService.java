package com.juanfe.notifications_microservice.service;

import com.juanfe.notifications_microservice.dto.NotificationEvent;
import com.juanfe.notifications_microservice.model.Notification;
import com.juanfe.notifications_microservice.model.NotificationCategory;
import com.juanfe.notifications_microservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


//this service is in charge of handling the notifications that are sent to the kafka topic, 
//it saves the notifications in the database and triggers the notification sender service
//this notification sender service could be implemented asyncronously with a tool like temporal 
//that ensures asyncronus workflows with multiple steps are properly handled
@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationSenderService notificationSenderService;

    public NotificationService(NotificationRepository notificationRepository, NotificationSenderService notificationSenderService) {
        this.notificationRepository = notificationRepository;
        this.notificationSenderService = notificationSenderService;
    }

    @KafkaListener(topics = "level-up", groupId = "notifications", containerFactory = "notificationEventKafkaListenerContainerFactory")
    public void handleLevelUp(NotificationEvent payload) {
        //as soon as we get an event we create a notification and save it in the database
        Notification notification = new Notification();
        notification.setEventType(payload.getEventType());
        String details = payload.getEventDetails().orElse("");
        notification.setContent("Congratulations! You've reached level " + details + "!");
        notification.setRecipient(payload.getRecipient());
        notification.setSender(payload.getSender().orElse(null));
        notification.setStatus("UNREAD");
        notification.setTimestamp(System.currentTimeMillis());
        // another way to set the category is to use the enum directly
        // notification.setCategory(NotificationCategory.fromEventType(payload.getEventType()));
        //but since we stablished a topic for each event type we can just set the category directly
        notification.setCategory(NotificationCategory.GAME_EVENT);
        notificationSenderService.handleNotification(notificationRepository.save(notification));
    }

    @KafkaListener(topics = "item-acquired", groupId = "notifications", containerFactory = "notificationEventKafkaListenerContainerFactory")
    public void handleItemAcquired(NotificationEvent payload) {
        Notification notification = new Notification();
        notification.setEventType(payload.getEventType());
        String details = payload.getEventDetails().orElse("");
        notification.setContent("Awesome! You've obtained: " + details);
        notification.setRecipient(payload.getRecipient());
        notification.setSender(payload.getSender().orElse(null));
        notification.setStatus("UNREAD");
        notification.setTimestamp(System.currentTimeMillis());
        notification.setCategory(NotificationCategory.GAME_EVENT);
        notificationSenderService.handleNotification(notificationRepository.save(notification));
    }

    @KafkaListener(topics = "challenge-completed", groupId = "notifications", containerFactory = "notificationEventKafkaListenerContainerFactory")
    public void handleChallengeCompleted(NotificationEvent payload) {
        Notification notification = new Notification();
        notification.setEventType(payload.getEventType());
        String details = payload.getEventDetails().orElse("");
        notification.setContent("Well done! Challenge completed: " + details);
        notification.setRecipient(payload.getRecipient());
        notification.setSender(payload.getSender().orElse(null));
        notification.setStatus("UNREAD");
        notification.setTimestamp(System.currentTimeMillis());
        notification.setCategory(NotificationCategory.GAME_EVENT);
        notificationSenderService.handleNotification(notificationRepository.save(notification));
    }

    @KafkaListener(topics = "pvp-event", groupId = "notifications", containerFactory = "notificationEventKafkaListenerContainerFactory")
    public void handlePvPEvent(NotificationEvent payload) {
        Notification notification = new Notification();
        notification.setEventType(payload.getEventType());
        String details = payload.getEventDetails().orElse("");
        notification.setContent("PvP Alert: " + details);
        notification.setRecipient(payload.getRecipient());
        notification.setSender(payload.getSender().orElse(null));
        notification.setStatus("UNREAD");
        notification.setTimestamp(System.currentTimeMillis());
        notification.setCategory(NotificationCategory.GAME_EVENT);
        notificationSenderService.handleNotification(notificationRepository.save(notification));
    }

    @KafkaListener(topics = "friend-request", groupId = "notifications", containerFactory = "notificationEventKafkaListenerContainerFactory")
    public void handleFriendRequest(NotificationEvent payload) {
        Notification notification = new Notification();
        notification.setEventType(payload.getEventType());
        String senderStr = payload.getSender().map(Object::toString).orElse("Unknown");
        notification.setContent("You have a new friend request from: " + senderStr);
        notification.setRecipient(payload.getRecipient());
        notification.setSender(payload.getSender().orElse(null));
        notification.setStatus("UNREAD");
        notification.setTimestamp(System.currentTimeMillis());
        notification.setCategory(NotificationCategory.SOCIAL_EVENT);
        notificationSenderService.handleNotification(notificationRepository.save(notification));
    }

    @KafkaListener(topics = "friend-accepted", groupId = "notifications", containerFactory = "notificationEventKafkaListenerContainerFactory")
    public void handleFriendAccepted(NotificationEvent payload) {
        Notification notification = new Notification();
        notification.setEventType(payload.getEventType());
        String senderStr = payload.getSender().map(Object::toString).orElse("Unknown");
        notification.setContent(senderStr + " accepted your friend request!");
        notification.setRecipient(payload.getRecipient());
        notification.setSender(payload.getSender().orElse(null));
        notification.setStatus("UNREAD");
        notification.setTimestamp(System.currentTimeMillis());
        notification.setCategory(NotificationCategory.SOCIAL_EVENT);
        notificationSenderService.handleNotification(notificationRepository.save(notification));
    }

    @KafkaListener(topics = "new-follower", groupId = "notifications", containerFactory = "notificationEventKafkaListenerContainerFactory")
    public void handleNewFollower(NotificationEvent payload) {
        Notification notification = new Notification();
        notification.setEventType(payload.getEventType());
        String senderStr = payload.getSender().map(Object::toString).orElse("Unknown");
        notification.setContent("You have a new follower: " + senderStr);
        notification.setRecipient(payload.getRecipient());
        notification.setSender(payload.getSender().orElse(null));
        notification.setStatus("UNREAD");
        notification.setTimestamp(System.currentTimeMillis());
        notification.setCategory(NotificationCategory.SOCIAL_EVENT);
        notificationSenderService.handleNotification(notificationRepository.save(notification));
    }
}
