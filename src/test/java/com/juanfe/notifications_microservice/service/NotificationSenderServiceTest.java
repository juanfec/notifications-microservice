package com.juanfe.notifications_microservice.service;

import com.juanfe.notifications_microservice.exception.NotificationRecipientUserNotFound;
import com.juanfe.notifications_microservice.model.Notification;
import com.juanfe.notifications_microservice.model.NotificationCategory;
import com.juanfe.notifications_microservice.model.User;
import com.juanfe.notifications_microservice.repository.NotificationRepository;
import com.juanfe.notifications_microservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class NotificationSenderServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private RealtimeNotificationService realtimeNotificationService;

    @InjectMocks
    private NotificationSenderService notificationSenderService;

    private User userWithAllPrefs(UUID id) {
        User user = new User();
        user.setId(id);
        Map<String, Boolean> notificationPreferences = new HashMap<>(Map.of(
                "GAME_EVENT", true,
                "SOCIAL_EVENT", true
        ));
        user.setNotificationPreferences(notificationPreferences);
        return user;
    }

    private Notification buildNotification(UUID recipientId, NotificationCategory category) {
        Notification n = new Notification();
        n.setRecipient(recipientId);
        n.setCategory(category);
        n.setContent("Test content");
        n.setStatus("UNREAD");
        n.setEventType("level-up");
        n.setSender((UUID.randomUUID()));
        n.setTimestamp(System.currentTimeMillis());
        return n;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should send notification if user has category enabled")
    void testHandleNotificationSendsIfEnabled() {
        UUID userId = UUID.randomUUID();
        Notification notification = buildNotification(userId, NotificationCategory.GAME_EVENT);
        User user = userWithAllPrefs(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        notificationSenderService.handleNotification(notification);

        verify(realtimeNotificationService).sendToUser(eq(userId.toString()), eq(notification.getContent()));
        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    @DisplayName("Should not send notification if user has category disabled")
    void testHandleNotificationDoesNotSendIfDisabled() {
        UUID userId = UUID.randomUUID();
        Notification notification = buildNotification(userId, NotificationCategory.GAME_EVENT);
        User user = new User();
        user.setId(userId);
        Map<String, Boolean> notificationPreferences = new HashMap<>(Map.of(
            "GAME_EVENT", false,
            "SOCIAL_EVENT", true
        ));
        user.setNotificationPreferences(notificationPreferences);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        notificationSenderService.handleNotification(notification);

        verify(realtimeNotificationService, never()).sendToUser(anyString(), anyString());
        verify(notificationRepository, never()).save(any(Notification.class));
    }

    @Test
    @DisplayName("Should throw if recipient not found")
    void testHandleNotificationThrowsIfRecipientNotFound() {
        UUID userId = UUID.randomUUID();
        Notification notification = buildNotification(userId, NotificationCategory.GAME_EVENT);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> notificationSenderService.handleNotification(notification))
            .isInstanceOf(NotificationRecipientUserNotFound.class)
            .hasMessageContaining("Recipient user not found");
    }
}
