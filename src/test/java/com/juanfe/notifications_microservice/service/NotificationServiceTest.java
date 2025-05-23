package com.juanfe.notifications_microservice.service;

import com.juanfe.notifications_microservice.dto.NotificationEvent;
import com.juanfe.notifications_microservice.model.Notification;
import com.juanfe.notifications_microservice.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

class NotificationServiceTest {
    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private NotificationSenderService notificationSenderService;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Ensure notificationSenderService is a mock and not null
        if (notificationSenderService == null) {
            notificationSenderService = Mockito.mock(NotificationSenderService.class);
        }
    }

    @Test
    @DisplayName("Should create and save a level-up notification")
    void testHandleLevelUp() {
        NotificationEvent event = NotificationTestData.createLevelUpEvent();

        notificationService.handleLevelUp(event);

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository).save(captor.capture());
        Notification saved = captor.getValue();
        assertThat(saved.getEventType()).isEqualTo(event.getEventType());
        assertThat(saved.getContent()).contains(event.getEventDetails().get());
        assertThat(saved.getRecipient()).isEqualTo(event.getRecipient());
        assertThat(saved.getSender()).isEqualTo(event.getSender().get());
        assertThat(saved.getStatus()).isEqualTo("UNREAD");
    }

    @Test
    @DisplayName("Should create and save an item-acquired notification")
    void testHandleItemAcquired() {
        NotificationEvent event = NotificationTestData.createItemAcquiredEvent();

        notificationService.handleItemAcquired(event);

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository).save(captor.capture());
        Notification saved = captor.getValue();
        assertThat(saved.getEventType()).isEqualTo(event.getEventType());
        assertThat(saved.getContent()).contains(event.getEventDetails().get());
        assertThat(saved.getRecipient()).isEqualTo(event.getRecipient());
        assertThat(saved.getSender()).isEqualTo(event.getSender().get());
        assertThat(saved.getStatus()).isEqualTo("UNREAD");
    }

    @Test
    @DisplayName("Should create and save a challenge-completed notification")
    void testHandleChallengeCompleted() {
        NotificationEvent event = NotificationTestData.createChallengeCompletedEvent();

        notificationService.handleChallengeCompleted(event);

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository).save(captor.capture());
        Notification saved = captor.getValue();
        assertThat(saved.getEventType()).isEqualTo(event.getEventType());
        assertThat(saved.getContent()).contains(event.getEventDetails().get());
        assertThat(saved.getRecipient()).isEqualTo(event.getRecipient());
        assertThat(saved.getSender()).isEqualTo(event.getSender().get());
        assertThat(saved.getStatus()).isEqualTo("UNREAD");
    }

    @Test
    @DisplayName("Should create and save a PvP event notification")
    void testHandlePvPEvent() {
        NotificationEvent event = NotificationTestData.createPvPEvent();

        notificationService.handlePvPEvent(event);

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository).save(captor.capture());
        Notification saved = captor.getValue();
        assertThat(saved.getEventType()).isEqualTo(event.getEventType());
        assertThat(saved.getContent()).contains("PvP Alert: " + event.getEventDetails().get());
        assertThat(saved.getRecipient()).isEqualTo(event.getRecipient());
        assertThat(saved.getSender()).isEqualTo(event.getSender().get());
        assertThat(saved.getStatus()).isEqualTo("UNREAD");
    }

    @Test
    @DisplayName("Should create and save a friend request notification")
    void testHandleFriendRequest() {
        NotificationEvent event = NotificationTestData.createFriendRequestEvent();

        notificationService.handleFriendRequest(event);

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository).save(captor.capture());
        Notification saved = captor.getValue();
        assertThat(saved.getEventType()).isEqualTo(event.getEventType());
        assertThat(saved.getContent()).isEqualTo("You have a new friend request from: " + event.getSender().get());
        assertThat(saved.getRecipient()).isEqualTo(event.getRecipient());
        assertThat(saved.getSender()).isEqualTo(event.getSender().get());
        assertThat(saved.getStatus()).isEqualTo("UNREAD");
    }

    @Test
    @DisplayName("Should create and save a friend accepted notification")
    void testHandleFriendAccepted() {
        NotificationEvent event = NotificationTestData.createFriendAcceptedEvent();

        notificationService.handleFriendAccepted(event);

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository).save(captor.capture());
        Notification saved = captor.getValue();
        assertThat(saved.getEventType()).isEqualTo(event.getEventType());
        assertThat(saved.getContent()).isEqualTo(event.getSender().get() + " accepted your friend request!");
        assertThat(saved.getRecipient()).isEqualTo(event.getRecipient());
        assertThat(saved.getSender()).isEqualTo(event.getSender().get());
        assertThat(saved.getStatus()).isEqualTo("UNREAD");
    }

    @Test
    @DisplayName("Should create and save a new follower notification")
    void testHandleNewFollower() {
        NotificationEvent event = NotificationTestData.createNewFollowerEvent();

        notificationService.handleNewFollower(event);

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository).save(captor.capture());
        Notification saved = captor.getValue();
        assertThat(saved.getEventType()).isEqualTo(event.getEventType());
        assertThat(saved.getContent()).isEqualTo("You have a new follower: " + event.getSender().get());
        assertThat(saved.getRecipient()).isEqualTo(event.getRecipient());
        assertThat(saved.getSender()).isEqualTo(event.getSender().get());
        assertThat(saved.getStatus()).isEqualTo("UNREAD");
    }
}
