package com.juanfe.notifications_microservice.service;


import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

//suppose we have a WebSocket connection established with the user
// and we can send messages to a specific user using their session ID or username
@Service
public class RealtimeNotificationService {

    private SimpMessagingTemplate messagingTemplate;

    public void sendToUser(String userId, String payload) {
        // Sends the payload to the user-specific WebSocket destination
        // we could do server-side rendering or just send the string with the notification
        // it all depends on the frontend implementation
        // for now we are going to send the payload as a string
        messagingTemplate.convertAndSendToUser(userId, "/queue/notifications", payload);
    }
}
