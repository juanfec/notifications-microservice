package com.juanfe.notifications_microservice.service;

import com.juanfe.notifications_microservice.exception.NotificationRecipientUserNotFound;
import com.juanfe.notifications_microservice.model.Notification;
import com.juanfe.notifications_microservice.model.User;
import com.juanfe.notifications_microservice.repository.NotificationRepository;
import com.juanfe.notifications_microservice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationSenderService {

    //this could probably be a service that needs to impact a diferent microservice depending on the general architecture
    //for now we are going to supose we can get the user from the same microservice
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RealtimeNotificationService realtimeNotificationService;
    @Autowired
    private NotificationRepository notificationRepository;

    public NotificationSenderService(UserRepository userRepository, RealtimeNotificationService realtimeNotificationService, NotificationRepository notificationRepository) {
        this.realtimeNotificationService = realtimeNotificationService;
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    public void handleNotification(Notification notification) {
        Optional<User> recipient = userRepository.findById(notification.getRecipient());
        if (recipient.isEmpty()) {
            throw new NotificationRecipientUserNotFound("Recipient user not found for id: " + notification.getRecipient());
        }
        User user = recipient.get();
        boolean shouldSend = user.getNotificationPreferences()
            .getOrDefault(notification.getCategory().name(), false);
        if (!shouldSend) {
            // User has disabled this notification category
            return;
        }
        // we are going to asume we have a way to know if the user is online so
        // we can send realtime notifications to the game like this
        // boolean userIsOnline = recipient.get().isOnline();
        boolean userIsOnline = true; // TODO: implement this
        if(userIsOnline){
            // Send the notification to the user in real time
            realtimeNotificationService.sendToUser(notification.getRecipient().toString(), notification.getContent());
            notification.setStatus("SENT");
            notificationRepository.save(notification);
        }

        // Here we could also implement a diferent way to send the notification to the user
        // for example in an email or a push notification

    }
}
