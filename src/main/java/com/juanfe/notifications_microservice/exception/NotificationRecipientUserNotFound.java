package com.juanfe.notifications_microservice.exception;

public class NotificationRecipientUserNotFound extends RuntimeException {
    public NotificationRecipientUserNotFound(String message) {
        super(message);
    }
}
